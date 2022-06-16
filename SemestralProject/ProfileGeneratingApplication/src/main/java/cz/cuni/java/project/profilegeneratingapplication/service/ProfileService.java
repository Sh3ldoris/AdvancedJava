package cz.cuni.java.project.profilegeneratingapplication.service;

import com.itextpdf.html2pdf.HtmlConverter;
import cz.cuni.java.project.profilegeneratingapplication.model.UserProfile;
import cz.cuni.java.project.profilegeneratingapplication.repository.ProfileRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.xml.transform.StringSource;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringWriter;
import java.util.Base64;

@Service
public class ProfileService {

    private static final Logger LOGGER = LogManager.getLogger(ProfileService.class);

    @Value("${profile.service.xsl.destination}")
    private String PERSON_XSL_FILE_REF;
    private ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }


    public void generateAndSavePersonProfile(Long personId, String sourceXml) {
        try {
            String html;
            TransformerFactory factory = TransformerFactory.newInstance();
            Source xslt = new StreamSource(new File(PERSON_XSL_FILE_REF));
            Transformer transformer = factory.newTransformer(xslt);

            StringSource transformationSource = new StringSource(sourceXml);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);

            transformer.transform(transformationSource, result);

            html = writer.toString();
            String base64Html = Base64.getEncoder().encodeToString(html.getBytes());

            UserProfile userProfile = new UserProfile();
            userProfile.setId(personId);
            userProfile.setHtmlProfile(base64Html);

            profileRepository.save(userProfile);
        } catch (TransformerException e) {
            LOGGER.warn("Error while generating person profile");
            e.printStackTrace();
        }
    }

    public String getHtmlPersonProfile(Long personId) {
        if (personId == null ) {
            LOGGER.warn("Person id is null");
            return "";
        }

        UserProfile userProfile = profileRepository.findById(personId).orElse(null);

        if (userProfile == null) {
            LOGGER.warn("User profile is null");
            return "";
        }

        return userProfile.getHtmlProfileDecoded();
    }

    public byte[] getPdfPersonProfile(Long personId) {
        if (personId == null ) {
            LOGGER.warn("Person id is null");
            return new byte[] {};
        }

        UserProfile userProfile = profileRepository.findById(personId).orElse(null);

        if (userProfile == null) {
            LOGGER.warn("User profile is null");
            return new byte[] {};
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        HtmlConverter.convertToPdf(userProfile.getHtmlProfileDecoded(), baos);

        return baos.toByteArray();
    }
}
