package xmlws.roombooking.xmltools;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class RoomBookingSaxParser implements RoomBookingParser {

    public String tmpLocalName;

    /**
     * Parse an xml file provided as an input stream
     *
     * @param inputStream the input stream corresponding to the xml file
     * @return
     */
    public RoomBooking parse(InputStream inputStream) {
        RoomBooking roomBooking = new RoomBooking();

        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            SAXParser saxParser = spf.newSAXParser();
            saxParser.parse(inputStream, new RoomBookingHandler(roomBooking));
        } catch (Exception e) {
            e.printStackTrace();
            roomBooking = null;
        }
        return roomBooking;
    }

    private class RoomBookingHandler extends DefaultHandler {

        private RoomBooking roomBooking;

        public RoomBookingHandler(RoomBooking roomBooking) {
            this.roomBooking = roomBooking;
        }

        public void startElement(String namespaceURI,
                                 String localName,
                                 String qName,
                                 Attributes atts)
                throws SAXException {

            tmpLocalName = localName;
        }

        public void characters(char[] ch, int start, int length)
                throws SAXException {

            String valeur = new String(ch, start, length);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

            if (tmpLocalName.equals("label")) {
                roomBooking.setRoomLabel(valeur);
            } else if (tmpLocalName.equals("username")) {
                roomBooking.setUsername(valeur);
            } else if (tmpLocalName.equals("startDate")) {
                try {
                    roomBooking.setStartDate(dateFormat.parse(valeur));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else if (tmpLocalName.equals("endDate")) {
                try {
                    roomBooking.setEndDate(dateFormat.parse(valeur));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
