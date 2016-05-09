package com.excilys.cdb.model;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dto.model.ComputerDTO;

/**
 * Computer object Contain a row from computer table.
 */
public class Computer {
    private Long id = null;
    private String name = null;
    private LocalDate introduced = null;
    private LocalDate discontinued = null;
    private Company company = null;
    static final Logger LOGGER = LoggerFactory.getLogger(Computer.class);

    /**
     * Computer constructor.
     */
    public Computer() {
    };

    /**
     * Contructor without ID.
     *
     * @param name
     *            computer name
     * @param introduction
     *            introduction date (if exist)
     * @param discontinued
     *            discontinued date (if exist)
     * @param comp
     *            company name (if exist)
     */
    public Computer(String name, String introduction, String discontinued,
            Company comp) {
        LOGGER.debug("creating new Computer object without id");

        this.setName(name);
        if (introduction != null) {
            introduction = introduction.split(" ")[0];
            this.setIntroduced(LocalDate.parse(introduction));
        } else {
            introduction = null;
        }

        if (discontinued != null) {
            discontinued = discontinued.split(" ")[0];
            this.setDiscontinued(LocalDate.parse(discontinued));
        } else {
            discontinued = null;
        }
        if (comp != null) {
            this.setCompany(comp);
        } else {
            this.setCompany(null);
        }
    }

    /**
     * Computer constructor from ComputerDTO.
     * @param c source ComputerDTO
     */
    public Computer(ComputerDTO c) {
        this.id = c.getId();
        this.name = c.getName();

        if (c.getIntroduced().equals("")) {
            this.introduced = null;
        } else {
            this.setIntroduced(LocalDate.parse(c.getIntroduced()));
        }
        if (c.getDiscontinued().equals("")) {
            this.discontinued = null;
        } else {
            this.setIntroduced(LocalDate.parse(c.getDiscontinued()));
        }
    }

    /**
     * Contructor with an ID.
     *
     * @param id
     *            computer id
     * @param name
     *            computer name
     * @param introduced
     *            introduce date
     * @param discontinued
     *            discontinued date
     * @param comp
     *            company
     */
    public Computer(Long id, String name, String introduced,
            String discontinued, Company comp) {
        LOGGER.debug("creating new Computer object with id");

        this.id = id;
        this.name = name;

        if (introduced == null) {
            this.introduced = null;
        } else {
            introduced = introduced.split(" ")[0];
            this.introduced = LocalDate.parse(introduced);
        }

        if (discontinued == null) {
            this.discontinued = null;
        } else {
            discontinued = discontinued.split(" ")[0];
            this.discontinued = LocalDate.parse(discontinued);
        }
        this.setCompany(comp);
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public LocalDate getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(LocalDate discontinued) {
        this.discontinued = discontinued;
    }

    public LocalDate getIntroduced() {
        return introduced;
    }

    public void setIntroduced(LocalDate introduced) {
        this.introduced = introduced;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((company == null) ? 0 : company.hashCode());
        result = prime * result
                + ((discontinued == null) ? 0 : discontinued.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result
                + ((introduced == null) ? 0 : introduced.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Computer)) {
            return false;
        }
        Computer other = (Computer) obj;
        if (company == null) {
            if (other.company != null) {
                return false;
            }
        } else if (!company.equals(other.company)) {
            return false;
        }
        if (discontinued == null) {
            if (other.discontinued != null) {
                return false;
            }
        } else if (!discontinued.equals(other.discontinued)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (introduced == null) {
            if (other.introduced != null) {
                return false;
            }
        } else if (!introduced.equals(other.introduced)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    /**
     * return a Computer under str form.
     *
     * @return Computer str
     */
    public String toString() {
        StringBuffer s = new StringBuffer(name);
        if (introduced != null) {
            s.append(", introduced in ");
            s.append(this.introduced);
        }
        if (discontinued != null) {
            s.append(", discontinued in ");
            s.append(this.discontinued);
        }
        if (getCompany() != null) {
            s.append(", provided by ");
            s.append(this.getCompany().getName());
        }
        return s.toString();
    }

    /**
     * Builder pattern to create computer.
     */
    public static class Builder {
        private Long id = null;
        private String name = null;
        private String introduced = null;
        private String discontinued = null;
        private Company company = null;

        /**
         * Builder Constructor.
         */
        public Builder() {
        }

        /**
         * add id param to Builder.
         *
         * @param id
         *            fesh id param
         * @return Computer.Builder
         */
        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * add name param to Builder.
         *
         * @param name
         *            name fresh param
         * @return Computer.Builder
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * add introduced param to Builder.
         *
         * @param intro
         *            intro fresh param
         * @return Computer.Builder
         */
        public Builder introduced(String intro) {
            this.introduced = intro;
            return this;
        }

        /**
         * add discontinued param to Builder.
         *
         * @param discontinued
         *            discontinued fresh param
         * @return Computer.Builder
         */
        public Builder discontinued(String discontinued) {
            this.discontinued = discontinued;
            return this;
        }

        /**
         * add company param to Builder.
         *
         * @param company
         *            Company to add
         * @return Computer.Builder
         */
        public Builder company(Company company) {
            this.company = company;
            return this;
        }

        /**
         * method to build a Computer object from Builder class.
         *
         * @return Computer object
         */
        public Computer build() {
            return new Computer(id, name, introduced, discontinued, company);
        }
    }
}
