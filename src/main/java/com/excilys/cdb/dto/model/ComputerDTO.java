package com.excilys.cdb.dto.model;

import java.time.LocalDate;

import com.excilys.cdb.model.Computer;

/**
 * . ComputerDTO, use to transmit data to jsp from servlet
 *
 */
public class ComputerDTO {
    protected long id = -1;
    protected String name;
    protected String introduced;
    protected String discontinued;
    protected long idCompany;
    protected String nameCompany;

    /**.
     * ComputerDTO main constructor
     * @param c Computer to map
     */
    public ComputerDTO(Computer c) {
        this.id = c.getId();
        this.name = c.getName();
        LocalDate intro = c.getIntroduced();
        LocalDate discontinued = c.getDiscontinued();
        this.introduced = (intro != null) ? (intro.toString()) : ("");
        this.discontinued = (discontinued != null) ? (discontinued.toString())
                : ("");
        if (c.getCompany() != null) {
            this.idCompany = c.getCompany().getId();
            this.nameCompany = c.getCompany().getName();
        } else {
            this.idCompany = 0;
            this.nameCompany = "";
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduced() {
        return introduced;
    }

    public void setIntroduced(String introduction) {
        this.introduced = introduction;
    }

    public String getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(String discontinued) {
        this.discontinued = discontinued;
    }

    public long getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(long idCompany) {
        this.idCompany = idCompany;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((discontinued == null) ? 0 : discontinued.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + (int) (idCompany ^ (idCompany >>> 32));
        result = prime * result
                + ((introduced == null) ? 0 : introduced.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result
                + ((nameCompany == null) ? 0 : nameCompany.hashCode());
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
        if (!(obj instanceof ComputerDTO)) {
            return false;
        }
        ComputerDTO other = (ComputerDTO) obj;
        if (discontinued == null) {
            if (other.discontinued != null) {
                return false;
            }
        } else if (!discontinued.equals(other.discontinued)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (idCompany != other.idCompany) {
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
        if (nameCompany == null) {
            if (other.nameCompany != null) {
                return false;
            }
        } else if (!nameCompany.equals(other.nameCompany)) {
            return false;
        }
        return true;
    }
}
