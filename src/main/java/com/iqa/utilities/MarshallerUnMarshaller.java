package com.iqa.utilities;

import com.iqa.generated.qualifications.Qualifications;
import com.iqa.generated.qualifications.Shares;
import com.iqa.generated.qualifications.Skills;
import com.iqa.qualifications.model.QualificationsEntity;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

/**
 * Created by louis on 2018/05/22.
 */
public class MarshallerUnMarshaller {


    public List<Qualifications.Qualification> xmlToObjectQualifications(String qualificationInXml) {
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(Qualifications.class);


            StringReader xmlReader = new StringReader(qualificationInXml);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Qualifications qualifications = (Qualifications) jaxbUnmarshaller.unmarshal(xmlReader);
            return qualifications.getQualification();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String objectToXmlQualifications(QualificationsEntity qualificationsEntity) {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(QualificationsEntity.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            Writer xmlQualificationsWriter = new StringWriter();

            jaxbMarshaller.marshal(qualificationsEntity, xmlQualificationsWriter);

            return xmlQualificationsWriter.toString();

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Shares.ShareTo> xmlToObjectSharesTo(String sharesInXml) {
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(Shares.class);


            StringReader xmlReader = new StringReader(sharesInXml);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Shares sharesTo = (Shares) jaxbUnmarshaller.unmarshal(xmlReader);
            return sharesTo.getShareTo();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Shares.ShareFrom> xmlToObjectSharesFrom(String sharesInXml) {
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(Shares.class);


            StringReader xmlReader = new StringReader(sharesInXml);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Shares sharesFrom = (Shares) jaxbUnmarshaller.unmarshal(xmlReader);
            return sharesFrom.getShareFrom();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Shares xmlToObjectShares(String sharesInXml) {
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(Shares.class);
            StringReader xmlReader = new StringReader(sharesInXml);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Shares sharesFrom = (Shares) jaxbUnmarshaller.unmarshal(xmlReader);
            return sharesFrom;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Skills.Skill> xmlToObjectSkills(String skillsInXml) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Skills.class);
            StringReader xmlReader = new StringReader(skillsInXml);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Skills skills = (Skills) jaxbUnmarshaller.unmarshal(xmlReader);
            return skills.getSkill();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
