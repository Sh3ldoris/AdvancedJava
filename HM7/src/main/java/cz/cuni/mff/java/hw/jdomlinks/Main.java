package cz.cuni.mff.java.hw.jdomlinks;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {

    private static boolean isFirstSection = true;
    private static List<Element> allSections;


    public static void main(String[] argv) {
        try {
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(System.in);

            // load all sections elements from xml
            allSections = getAllSections(doc);
            // find all sections that have link inside them
            List<Element> allSectionsWithLinks = getAllSectionsWithLinks(allSections);

            // print on output
            printOutput(allSectionsWithLinks);
        } catch (IOException | JDOMException e) {
            e.printStackTrace();
        }
    }

    private static void printOutput(List<Element> linkSection) {
        // iterate every section with link inside
        for (Element section : linkSection) {

            // Print title of section
            printElementText(section.getChild("title"));
            System.out.print(":\n");

            // Load all links element that are in current section
            List<Element> links = new ArrayList<>();
            isFirstSection = true;
            findAllLinksInSection(section, links);

            // For every link prints its text (it can be nested in other elements)
            for (Element link : links) {
                System.out.print("    ");
                // Print link text (it can be nested)
                printElementText(link);
                System.out.print(" (");
                // Get linked identifier for linking section to current link
                String linkedId = getLinked(link);
                if (linkedId != null) {
                    // Print linked section's title
                    printLinkedText(linkedId);
                    System.out.print(")\n");
                } else {
                    System.out.println("Something is wrong!");
                }
            }
        }
    }

    /**
     * Look up for linked attribute
     * @param el given element
     * @return linked attribute's value
     */
    private static String getLinked(Element el) {
        for (Attribute at : el.getAttributes()) {
            if ("linkend".equals(at.getName())) {
                return at.getValue();
            }
        }

        return null;
    }

    /**
     * Prints text in given element (text could be nested in nested elements)
     * @param element
     */
    private static void printElementText(Element element) {
        System.out.print(element.getText());
        for (Element children : element.getChildren()) {
            printElementText(children);
        }
    }

    /**
     * Searches for section that contains given ID and prints the text inside it
     * @param id
     */
    private static void printLinkedText(String id) {
        // Iterate through all sections and search for given id
        for (Element section : allSections) {
            isFirstSection = true;
            if (hasLinkedId(section, id)) {
                printElementText(section.getChild("title"));
            }
        }
    }

    /**
     * Searches for link elements in given element
     * @param el
     * @param links
     */
    private static void findAllLinksInSection(Element el, List<Element> links) {

        // If element is link add it and return
        if ("link".equals(el.getName())) {
            links.add(el);
            return;
        }

        // Ignore and return when there is nested section
        if (!isFirstSection && "section".equals(el.getName())) {
            return;
        }

        isFirstSection = false;

        // If link element is not found iterate element's children
        for (Element child : el.getChildren()) {
            findAllLinksInSection(child, links);
        }

        return;
    }

    /**
     * Searches for elements with name Section in given Document
     * @param doc
     * @return
     */
    private static List<Element> getAllSections(Document doc) {
        Iterator<?> iterator = doc.getDescendants(new ElementFilter("section"));
        List<Element> allSections = new ArrayList<>();
        while (iterator.hasNext()) {
            Element next = (Element) iterator.next();

            allSections.add(next);
        }
        return allSections;
    }

    /**
     * Searches for sections, which have link(s) inside them
     * @param sections should be all section that we want to check
     * @return sections containing links
     */
    private static List<Element> getAllSectionsWithLinks(List<Element> sections) {
        List<Element> sectionsWithLinks = new ArrayList<>();

        for (Element section: sections) {
            isFirstSection = true;
            if (hasLinkInside(section)) {
                sectionsWithLinks.add(section);
            }
        }

        return sectionsWithLinks;
    }

    /**
     * Checks if given element has link inside
     * This method ignore nested sections (it looks only in top section)
     * @param el
     * @return
     */
    private static boolean hasLinkInside(Element el) {

        // If iterated element is link return that given beginning element has link inside
        if ("link".equals(el.getName())) {
            return true;
        }

        // Ignore nested sections
        if (!isFirstSection && "section".equals(el.getName())) {
            return false;
        }

        isFirstSection = false;

        // Iterate through element's children
        for (Element child : el.getChildren()) {
            if (hasLinkInside(child)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if given element contains other element with attribute ID with given value
     * @param el checks element
     * @param id value of ID attribute
     * @return
     */
    private static boolean hasLinkedId(Element el, String id) {

        if (!isFirstSection && "section".equals(el.getName())) {
            return false;
        }

        isFirstSection = false;

        for (Attribute at : el.getAttributes()) {
            if ("id".equals(at.getName()) && id.equals(at.getValue())) {
                return true;
            }
        }

        for (Element child : el.getChildren()) {
            if (hasLinkedId(child, id)) {
                return true;
            }
        }

        return false;
    }
}

