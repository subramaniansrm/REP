/**
 * 
 */
package com.srm.coreframework.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.srm.coreframework.exception.CoreException;
import com.srm.coreframework.exception.DAOException;
import com.srm.coreframework.exception.ErrorMessageCode;
import com.srm.coreframework.i18n.CoreMessageSource;
import com.srm.coreframework.util.FileDTO;


/**
 *
 */
@Service
public class FileService  {

	private static final Logger logger = LoggerFactory.getLogger(FileService.class);
	@Autowired
	private CoreMessageSource messageSource;

	public FileDTO uploadFile(MultipartFile file, String filePath) throws CoreException, IOException {
		FileDTO fileDTO = new FileDTO();
		try {

			if (filePath != null) {

				String fileName = StringUtils.cleanPath(file.getOriginalFilename());
				StringBuffer fullPath = new StringBuffer(filePath);

				// Create directory If path not avaliable
				File files = new File(filePath);
				if (!files.exists()) {
					files.mkdirs();
				}

				fullPath.append("/").append(fileName);

				logger.info("========== Full Path ==================>" + fullPath.toString());

				final Path fileStorageLocation = Paths.get(fullPath.toString());

				Files.copy(file.getInputStream(), fileStorageLocation, StandardCopyOption.REPLACE_EXISTING);
				logger.info("========== File Uploaded SUcessfully ==================>");
				fileDTO.setMessage("SUCESS");
			}
		} catch (Exception exception) {
			logger.debug("Error===>" + exception.getMessage());
			fileDTO.setMessage("FAILURE");
			throw new CoreException(messageSource.getMessage(ErrorMessageCode.FILE_UPLOAD_DOWNLOAD));
		}

		return fileDTO;
	}

	/**
	 * 
	 * @param files
	 * @param filePath
	 * @return
	 * @throws CoreException
	 * @throws IOException
	 */
	public List<FileDTO> uploadFiles(MultipartFile[] files, String filePath) throws CoreException, IOException {
		FileDTO fileDTO = null;
		List<FileDTO> fileDTOList = new ArrayList<>();
		for (MultipartFile file : files) {
			fileDTO = uploadFile(file, filePath);
			fileDTOList.add(fileDTO);
		}
		return fileDTOList;
	}

	public Resource findByFileName(String filePath) throws DAOException, CoreException {
		try {
			logger.info("File Path=============>" + filePath);

			if (filePath != null) {
				Resource resource = new UrlResource("file:\\" + filePath);
				if (resource.exists()) {
					logger.info(" Inside File Exists====>");
					return resource;
				} else {
					throw new CoreException(messageSource.getMessage(ErrorMessageCode.FILE_NOT_FOUND));
				}
			} else {
				throw new CoreException(messageSource.getMessage(ErrorMessageCode.FILE_NOT_FOUND));
			}
		} catch (MalformedURLException ex) {
			throw new CoreException(messageSource.getMessage(ErrorMessageCode.FILE_NOT_FOUND));
		}
	}

}
