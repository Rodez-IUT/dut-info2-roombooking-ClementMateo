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

    public RoomBooking roomBooking = new RoomBooking();

    /**
     * Parse an xml file provided as an input stream
     *
     * @param inputStream the input stream corresponding to the xml file
     * @return
     */
    public RoomBooking parse(InputStream inputStream) {

        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            SAXParser saxParser = spf.newSAXParser();
            saxParser.parse(inputStream, new RoomBookingHandler());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roomBooking;
    }

    private class RoomBookingHandler extends DefaultHandler {

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
            SimpleDateFormat dateDebut = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"),
                    dateFin = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

            if (!valeur.equals('\n')) {
                if (tmpLocalName.equals("label")) {
                    roomBooking.setRoomLabel(valeur);
                }
                if (tmpLocalName.equals("username")) {
                    roomBooking.setUsername(valeur);
                }
                if (tmpLocalName.equals("startDate")) {
                    try {
                        roomBooking.setStartDate(dateDebut.parse(valeur));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (tmpLocalName.equals("endDate")) {
                    try {
                        roomBooking.setEndDate(dateFin.parse(valeur));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
