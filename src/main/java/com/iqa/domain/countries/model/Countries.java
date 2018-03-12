package com.iqa.domain.countries.model;

/**
 * Created by louis on 2018/02/09.
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "countries", schema = "", catalog = "")
public class Countries {
    private int id;
    private String code;
    private String name;
    private String currency;
    private String currencyName;
    private String iso2Code;
    private String continent;
    private String oldCountryCode;
    private int countryPartnerId;
    private String dialingCode;
    private Integer utcOffset;

    @javax.persistence.Id
    @javax.persistence.Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "code", nullable = false, length = 3)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "currency", nullable = false, length = 3)
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "currency_name", nullable = false, length = 255)
    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "iso_2_code", nullable = true, length = 2)
    public String getIso2Code() {
        return iso2Code;
    }

    public void setIso2Code(String iso2Code) {
        this.iso2Code = iso2Code;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "continent", nullable = false, length = 15)
    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "old_country_code", nullable = true, length = 3)
    public String getOldCountryCode() {
        return oldCountryCode;
    }

    public void setOldCountryCode(String oldCountryCode) {
        this.oldCountryCode = oldCountryCode;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "country_partner_id", nullable = false)
    public int getCountryPartnerId() {
        return countryPartnerId;
    }

    public void setCountryPartnerId(int countryPartnerId) {
        this.countryPartnerId = countryPartnerId;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "dialing_code", nullable = true, length = 45)
    public String getDialingCode() {
        return dialingCode;
    }

    public void setDialingCode(String dialingCode) {
        this.dialingCode = dialingCode;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "utc_offset", nullable = true)
    public Integer getUtcOffset() {
        return utcOffset;
    }

    public void setUtcOffset(Integer utcOffset) {
        this.utcOffset = utcOffset;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;

        Countries that = (Countries) object;

        if (id != that.id) return false;
        if (countryPartnerId != that.countryPartnerId) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) return false;
        if (currencyName != null ? !currencyName.equals(that.currencyName) : that.currencyName != null) return false;
        if (iso2Code != null ? !iso2Code.equals(that.iso2Code) : that.iso2Code != null) return false;
        if (continent != null ? !continent.equals(that.continent) : that.continent != null) return false;
        if (oldCountryCode != null ? !oldCountryCode.equals(that.oldCountryCode) : that.oldCountryCode != null)
            return false;
        if (dialingCode != null ? !dialingCode.equals(that.dialingCode) : that.dialingCode != null) return false;
        if (utcOffset != null ? !utcOffset.equals(that.utcOffset) : that.utcOffset != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (currencyName != null ? currencyName.hashCode() : 0);
        result = 31 * result + (iso2Code != null ? iso2Code.hashCode() : 0);
        result = 31 * result + (continent != null ? continent.hashCode() : 0);
        result = 31 * result + (oldCountryCode != null ? oldCountryCode.hashCode() : 0);
        result = 31 * result + countryPartnerId;
        result = 31 * result + (dialingCode != null ? dialingCode.hashCode() : 0);
        result = 31 * result + (utcOffset != null ? utcOffset.hashCode() : 0);
        return result;
    }
}
