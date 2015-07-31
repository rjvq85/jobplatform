package pt.criticalsoftware.domain.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name="entrevista")
@Table(name="entrevistas")
public class InterviewEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="data_entrevista",nullable=false)
	private LocalDate date;
	
	@Column(name="feedback")
	private String feedback;
	
	@ManyToOne(targetEntity=UserEntity.class)
	private UserEntity interviewer;

}
