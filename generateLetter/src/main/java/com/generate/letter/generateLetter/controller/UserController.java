package com.generate.letter.generateLetter.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.allcolor.yahp.converter.CYaHPConverter;
import org.allcolor.yahp.converter.IHtmlToPdfTransformer;
import org.allcolor.yahp.converter.IHtmlToPdfTransformer.CConvertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.generate.letter.generateLetter.model.ReleaseModel;
import com.generate.letter.generateLetter.security.JwtTokenUtil;
import com.generate.letter.generateLetter.utility.ApplicationUtility;
import com.generate.letter.generateLetter.utility.VelocityUtility;

@RestController
public class UserController {

	@Value("${jwt.header}")
	private String tokenHeader;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private ApplicationUtility applicationUtility;

	@Autowired
	VelocityUtility velocityUtility;

	@Value("${default.pdf.path}")
	String defaultPdfPath;

	@RequestMapping(value = "${api.route.generateExpLetter}", method = RequestMethod.POST)
	public void generateExperienceLetter(HttpServletRequest request, @Valid @RequestBody ReleaseModel releaseModel,
			BindingResult result, HttpServletResponse httpServletResponse) throws CConvertException, IOException {
		System.out.println("generateExperienceLetter called" + releaseModel.toString());

		File pdfFile = generateTemplate(releaseModel);

		httpServletResponse.setContentType("application/pdf");
		httpServletResponse.setHeader("Content-Disposition", "filename=" + pdfFile.getName());

		FileInputStream inputStream = new FileInputStream(new File(pdfFile.getPath()));
		BufferedInputStream inStrem = new BufferedInputStream(new FileInputStream(pdfFile));
		BufferedOutputStream outStream = new BufferedOutputStream(httpServletResponse.getOutputStream());

		byte[] buffer = new byte[2048];
		int bytesRead = 0;
		while ((bytesRead = inStrem.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}
		outStream.flush();
		inStrem.close();

	}

	public File generateTemplate(ReleaseModel releaseModel) throws FileNotFoundException, CConvertException {
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("name", releaseModel.getName());
		
		/** joining/release date **/
		Date jDate = releaseModel.getJoiningDate();
		String joiningDate = getDate(jDate);
		props.put("jdate", joiningDate);

		Date rdate = releaseModel.getRelievingDate();
		String releaseDate = getDate(rdate);
		props.put("rdate", releaseDate);

		System.out.println(jDate + "---------" + rdate);
		/** joining/release date end **/
		
		
		/** release suffix  date **/
		String suffix = getSuffixDate(rdate);
		String suffixMonth = getMonth(rdate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(rdate);
		String day = new Integer(cal.get(Calendar.DAY_OF_MONTH)).toString();
		if (cal.get(Calendar.DAY_OF_MONTH) < 9) {
			day = "0" + day;
		}
		String year = new Integer(cal.get(Calendar.YEAR)).toString();
		
		props.put("suffixDay", day);
		props.put("suffixMonth", suffixMonth);
		props.put("suffixYear", year);
		props.put("suffix", suffix);
		
		/** release suffix  date end **/
		
		String gender = "She";
		String genderPro = "her";
		String genderPro1 = "her";
		if (releaseModel.getGender().equalsIgnoreCase("male")) {
			gender = "He";
			genderPro = "his";
			genderPro1 = "him";
		}
		props.put("gender", gender);
		props.put("genderPro", genderPro);
		props.put("genderPro1", genderPro1);
		props.put("designation", releaseModel.getDesignation());

		ClassPathResource wazooDualLogo = new ClassPathResource("classpath:images/agsft_logo.png");
		System.out.println(wazooDualLogo);
		props.put("agsftLogo", wazooDualLogo.getPath());
		String pdfTemplate = velocityUtility.getTemplatetoText("templates/releaseLetter.vm", props);

		CYaHPConverter converter = new CYaHPConverter();
		Map properties = new HashMap();
		properties.put(IHtmlToPdfTransformer.PDF_RENDERER_CLASS, IHtmlToPdfTransformer.FLYINGSAUCER_PDF_RENDERER);

		List headerFooterList = new ArrayList();

		String dateTime = new SimpleDateFormat("yyyMMdd_hhmmss").format(new Date());
		String fileName = releaseModel.getName() + "_" + dateTime;

		// @Cleanup
		FileOutputStream fos = new FileOutputStream(
				new File(defaultPdfPath.concat("/").concat(fileName).concat(".pdf")));

		String baseUrlPath = new StringBuffer().append("file://").append(System.getProperty("java.io.tmpdir"))
				.toString();

		converter.convertToPdf(pdfTemplate, IHtmlToPdfTransformer.A4P, headerFooterList, baseUrlPath, fos, properties);

		Path filePath = Paths.get(defaultPdfPath.concat("/")).resolve((fileName).concat(".pdf"));
		return filePath.toFile();

	}

	public String getDate(Date userDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(userDate);
		String day = new Integer(cal.get(Calendar.DAY_OF_MONTH)).toString();
		if (cal.get(Calendar.DAY_OF_MONTH) < 9) {
			day = "0" + day;
		}
		String month = new Integer(cal.get(Calendar.MONTH) + 1).toString();
		if ((cal.get(Calendar.MONTH) + 1) < 9) {
			month = "0" + month;
		}
		int year = cal.get(Calendar.YEAR);

		String val = day + "/" + month + "/" + year;
		return val;
	}

	public String getSuffixDate(Date userDate) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(userDate);
		int n = cal.get(Calendar.DAY_OF_MONTH);
		String suff = "th";
		// checkArgument(n >= 1 && n <= 31, "illegal day of month: " + n);
		if (n >= 11 && n <= 13) {
			suff = "th";
		} else {
			switch (n % 10) {
			case 1:
				suff = "st";
				break;
			case 2:
				suff = "nd";
				break;
			case 3:
				suff = "rd";
				break;
			default:
				suff = "th";
				break;
			}
		}

		return suff;
	}
	
	public String getMonth(Date userDate) {
		

		String[] monthName = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };

		
		Calendar cal = Calendar.getInstance();
		cal.setTime(userDate);
		String day = new Integer(cal.get(Calendar.DAY_OF_MONTH)).toString();
		if (cal.get(Calendar.DAY_OF_MONTH) < 9) {
			day = "0" + day;
		}
		String month = monthName[cal.get(Calendar.MONTH)];
		return month;
	}

}
