package com.lunarsystems.model;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;


public class Missao implements Serializable {
private String codigo; 
private String nome;
private LocalDate dataLancamento;
private LocalDate dataRetorno;
private String destino; 
private String objetivo;
private String resultado; 
private Nave nave;
private List<Astronauta> astronautas = new ArrayList<>();


public Missao(String codigo, String nome, LocalDate dataLancamento, String destino, String objetivo, Nave nave) {
this.codigo = codigo;
this.nome = nome;
this.dataLancamento = dataLancamento;
this.destino = destino;
this.objetivo = objetivo;
this.nave = nave;
}


public String getCodigo() { return codigo; }
public String getNome() { return nome; }
public LocalDate getDataLancamento() { return dataLancamento; }
public LocalDate getDataRetorno() { return dataRetorno; }
public void setDataRetorno(LocalDate dataRetorno) { this.dataRetorno = dataRetorno; }
public String getDestino() { return destino; }
public String getObjetivo() { return objetivo; }
public String getResultado() { return resultado; }
public void setResultado(String resultado) { this.resultado = resultado; }
public Nave getNave() { return nave; }
public List<Astronauta> getAstronautas() { return astronautas; }


public void addAstronauta(Astronauta a) { astronautas.add(a); }


public long getDuracaoDias() {
if (dataLancamento == null || dataRetorno == null) return -1;
return Period.between(dataLancamento, dataRetorno).getDays();
}


@Override
public String toString() {
return "[" + codigo + "] " + nome + " - " + destino + " - " + nave + " - tripulação=" + astronautas.size();
}
}