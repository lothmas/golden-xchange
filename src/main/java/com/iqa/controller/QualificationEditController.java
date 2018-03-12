package com.iqa.controller;

/**
 * Created by louis on 2018/08/10.
 */

import com.iqa.generated.qualifications.Qualifications;
import com.iqa.profile.individual.service.IndividualProfileService;
import com.iqa.qualifications.exception.QualificationsNotFoundException;
import com.iqa.qualifications.model.QualificationsEntity;
import com.iqa.qualifications.service.QualificationsService;
import com.iqa.utilities.MarshallerUnMarshaller;
import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.QualifiedIdentifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
public class QualificationEditController {
    MarshallerUnMarshaller marshallerUnMarshaller = new MarshallerUnMarshaller();

    @Autowired
    IndividualProfileService individualProfileService;

    @Autowired
    QualificationsService qualificationsService;


    @RequestMapping(value = "/qualification_edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String editQualification(@RequestParam("current") String current, @RequestParam("previous") String previous, @RequestParam("action") String action, HttpSession session) {

        try {
            //  if (!current.equals(previous)) {
            QualificationsEntity qualifications = qualificationsService.findQualificationsByUsername(session.getAttribute("usernameForEditedQualification").toString());
            List<Qualifications.Qualification> qualificationsLists = marshallerUnMarshaller.xmlToObjectQualifications(qualifications.getQualifications());
            int selectedQualification = Integer.valueOf(session.getAttribute("selectedQualification").toString());
            List<Qualifications.Qualification.Module> modules = qualificationsLists.get(selectedQualification).getModule();

            ArrayList array = jsToJavaArray(previous);
            int countModule = 1;
            int countGrade = 1;
            int selectedModule = Integer.valueOf(session.getAttribute("selectedModule").toString());
            int selectedGrade;

            for (Qualifications.Qualification.Module module : modules) {
                if (selectedModule == countModule) {
                    if (action.equals("addGrade")) {
                        addGradeQualification(selectedQualification, selectedModule, array.get(0).toString(), array.get(1).toString(), array.get(2).toString(), qualifications);
                    }
                    List<Qualifications.Qualification.Module.Grades> grades = module.getGrades();
                    for (Qualifications.Qualification.Module.Grades grade : grades) {
                        if (array.get(0).equals(grade.getModuleCode())) {
                            System.out.println(grade.getSubject());
                            selectedGrade = countGrade;
                            selectedModule = countModule;
                            if (action.equals("editGrade")) {
                                editQualification(array.get(0).toString(), current, selectedQualification, selectedModule, selectedGrade, qualifications);
                            }

                            if (action.equals("deleteGrade")) {
                                deleteGradeQualification(selectedQualification, selectedModule, selectedGrade, qualifications);
                            }


                        }
                        countGrade++;
                    }
                }
                countModule++;
            }
//                            StringUtils.removeEnd(gradedPrint.toString(), ",");
//                            gradesForModules = StringUtils.removeEnd(gradedPrint.toString(), ",") + "";

//            }

        } catch (QualificationsNotFoundException e) {
            e.printStackTrace();
        }


        // your logic here
        return "";
    }

    private ArrayList jsToJavaArray(@RequestParam("previous") String previous) {
        List<String> wordList = Arrays.asList(previous);

        Pattern p = Pattern.compile("\"([^\"]*)\"");
        Matcher m = p.matcher(previous);
        ArrayList array = new ArrayList();

        while (m.find()) {
            array.add(m.group(1));

        }
        return array;
    }

    public boolean editQualification(String moduleCodeToEdit, String current, int selectedQualification, int selectedModule, int selectedGrade, QualificationsEntity qualificationsEntity) {
        SAXBuilder builder = new SAXBuilder();
        try {
            Document document = builder.build(
                    new ByteArrayInputStream(qualificationsEntity.getQualifications().getBytes()));

            Element qualification = document.getRootElement().getChildren("qualification").get(selectedQualification);

            Element module = qualification.getChildren("module").get(selectedModule - 1);

            Element grades = module.getChildren("grades").get(selectedGrade - 1);

            ArrayList array = jsToJavaArray(current);
            if (moduleCodeToEdit.equals(grades.getChildText("moduleCode").toString())) {
                grades.getChild("moduleCode").setText(array.get(0).toString());
                grades.getChild("subject").setText(array.get(1).toString());
                grades.getChild("grade").setText(array.get(2).toString());
            }

            XMLOutputter outputter =
                    new XMLOutputter(Format.getPrettyFormat());
            String editedXML = outputter.outputString(document);
            qualificationsEntity.setQualifications(editedXML);
            qualificationsService.saveQualifications(qualificationsEntity);

        } catch (Exception ex) {

        }
//        catch (JDOMException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        return false;
    }

    public boolean deleteGradeQualification(int selectedQualification, int selectedModule, int selectedGrade, QualificationsEntity qualificationsEntity) {
        SAXBuilder builder = new SAXBuilder();
        try {
            Document document = builder.build(
                    new ByteArrayInputStream(qualificationsEntity.getQualifications().getBytes()));

            Element qualification = document.getRootElement().getChildren("qualification").get(selectedQualification);

            Element module = qualification.getChildren("module").get(selectedModule - 1);

            module.getChildren("grades").remove(selectedGrade - 1);


            XMLOutputter outputter =
                    new XMLOutputter(Format.getPrettyFormat());
            String editedXML = outputter.outputString(document);
            qualificationsEntity.setQualifications(editedXML);
            qualificationsService.saveQualifications(qualificationsEntity);
            return true;

        } catch (Exception ex) {

        }


        return false;
    }


    public boolean addGradeQualification(int selectedQualification, int selectedModule, String moduleCode, String subject, String grade, QualificationsEntity qualificationsEntity) {
        SAXBuilder builder = new SAXBuilder();
        try {
            Document document = builder.build(
                    new ByteArrayInputStream(qualificationsEntity.getQualifications().getBytes()));

            Element qualification = document.getRootElement().getChildren("qualification").get(selectedQualification);

            Element module = qualification.getChildren("module").get(selectedModule - 1);

            module.addContent(new Element("grades")
                    .addContent(new Element("moduleCode").setText(moduleCode))
                    .addContent(new Element("subject").setText(subject))
                    .addContent(new Element("grade").setText(grade)));

            XMLOutputter outputter =
                    new XMLOutputter(Format.getPrettyFormat());
            String editedXML = outputter.outputString(document);
            qualificationsEntity.setQualifications(editedXML);
            qualificationsService.saveQualifications(qualificationsEntity);
            return true;

        } catch (Exception ex) {

        }


        return false;
    }


}
