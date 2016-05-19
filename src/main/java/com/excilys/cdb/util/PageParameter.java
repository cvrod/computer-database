package com.excilys.cdb.util;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.model.ComputerDTO;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.pagination.Page;
import com.excilys.cdb.service.ComputerServiceImpl;

@Component
public class PageParameter {
    static final Logger LOGGER = LoggerFactory.getLogger(PageParameter.class);

    @Autowired
    @Qualifier("computerService")
    private ComputerServiceImpl computerService;
    private int offset = 10;
    private int currentPage = 0;
    private String search;
    private String order;
    private String dir;
    private Long countComputer = new Long(0);

    private Page<ComputerDTO> computerDtoPage = null;

    /**
     * Default Constructor.
     */
    public PageParameter() {

    }

    /**
     * PageParameter constructor.
     *
     * @param offsetStr
     *            offset parameter
     * @param pageStr
     *            page parameter
     * @param search
     *            search parameter
     * @param order
     *            order parameter
     * @param dir
     *            direction parameter
     */
    public PageParameter(String offsetStr, String pageStr, String search,
            String order, String dir) {
        if (isPresent(offsetStr)) {
            try {
                setOffset(Integer.parseInt(offsetStr));
                LOGGER.info("getting offset : " + getOffset());
            } catch (NumberFormatException e) {
                LOGGER.debug("NumberFormatException on index param !");
                setOffset(10);
            }
        }
        if (isPresent(pageStr)) {
            try {
                setCurrentPage(Integer.parseInt(pageStr));
                LOGGER.info("getting current page : " + getCurrentPage());
            } catch (NumberFormatException e) {
                LOGGER.debug("NumberFormatException on page param !");
                setCurrentPage(0);
            }
        }

        this.setSearch(search);
        this.setOrder(order);
        this.setDir(dir);
    }

    /**
     * process url paramater and setting dtoPage and countComputer var.
     */
    public void process() {
        Page<Computer> computerPage = null;
        String order = this.getOrder();

        // if a direction is given we put it in the request
        if (isPresent(this.getOrder()) && isPresent(this.getDir())) {
            order += " " + this.getDir();
        }

        // if we dont have any search or order parameter -> return all computers
        if (!isPresent(this.getSearch()) && !isPresent(this.getOrder())) {
            computerPage = computerService
                    .listAllByPage(getCurrentPage() * getOffset(), getOffset());
            this.setCountComputer(computerService.count());
        } else if (!isPresent(this.getOrder())) { // default order -> by id
            computerPage = computerService.listByPage(this.getSearch(), "id",
                    getCurrentPage() * getOffset(), getOffset());
            this.setCountComputer(computerService.count(this.getSearch()));
        } else { // if we have search and order parameter
            computerPage = computerService.listByPage(this.getSearch(), order,
                    getCurrentPage() * getOffset(), getOffset());
            this.setCountComputer(computerService.count(this.getSearch()));
        }

        ArrayList<ComputerDTO> computerDtoArray = new ArrayList<>();
        ComputerDTO dtoTmp = null;

        // Convert Computer list in ComputerDTO list
        for (Computer c : computerPage.getElementList()) {
            dtoTmp = new ComputerDTO(c);
            computerDtoArray.add(dtoTmp);
        }
        this.setComputerDtoPage(new Page<>(computerDtoArray,
                computerPage.getStart(), computerPage.getOffset()));
    }

    /**
     * check if a given url parent is null or empty.
     *
     * @param param
     *            parameter to verify
     * @return boolean true if is present, false else
     */
    protected boolean isPresent(String param) {
        return !(param == null || param.equals(""));
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * offset setter from String param.
     * @param offset offset under str form
     */
    public void setOffset(String offset) {
        if (isPresent(offset)) {
            this.offset = Integer.parseInt(offset);
        } else {
            this.offset = 10;
        }
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * page setter from String param.
     * @param currentPage currentPage param under str form
     */
    public void setCurrentPage(String currentPage) {
        if (isPresent(currentPage)) {
            this.currentPage = Integer.parseInt(currentPage);
        } else {
            this.currentPage = 0;
        }
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public Long getCountComputer() {
        return countComputer;
    }

    public void setCountComputer(Long countComputer) {
        this.countComputer = countComputer;
    }

    public Page<ComputerDTO> getComputerDtoPage() {
        return computerDtoPage;
    }

    public void setComputerDtoPage(Page<ComputerDTO> computerDtoPage) {
        this.computerDtoPage = computerDtoPage;
    }
}
