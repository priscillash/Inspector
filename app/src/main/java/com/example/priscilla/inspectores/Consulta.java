package com.example.priscilla.inspectores;


public class Consulta {

    Integer idTicket;
    String matricula;
    String dateConsulta;

    public Consulta(Integer idTicket, String matricula, String dateConsulta) {
        this.idTicket = idTicket;
        this.matricula = matricula;
        this.dateConsulta = dateConsulta;
    }



    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getDateConsulta() {
        return dateConsulta;
    }

    public void setDateConsulta(String dateConsulta) {
        this.dateConsulta = dateConsulta;
    }

    public Integer getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Integer idTicket) {
        this.idTicket = idTicket;
    }


}
