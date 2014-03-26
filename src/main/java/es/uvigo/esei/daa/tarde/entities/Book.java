package es.uvigo.esei.daa.tarde.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "books")
public class Book extends Article {
}
