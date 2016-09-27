package com.smart.model.rule;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import com.smart.model.BaseObject;


@Entity
@Table(name = "lab_bag")
public class Bag extends BaseObject implements Serializable{

	
	private static final long serialVersionUID = 1L;
	// Primary Key
	private Long id;
	
	private String name;
	private Long parentID;
	private boolean isCore;
	private Set<Rule> rules = new HashSet<Rule>();
	
	private long hospitalId;
//	private Set<IDMap> idMap = new HashSet<IDMap>();

	/**
	 * Default constructor - creates a new instance with no values set.
	 */
	public Bag() {
	}

	/**
	 * 审核规则包id，自增
	 */
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BAG")
	@SequenceGenerator(name = "SEQ_BAG", sequenceName = "bag_sequence", allocationSize = 1)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * 审核规则包名称
	 */
	@Column(nullable = false, length = 20, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 父规则包的id
	 */
	@Column(name = "parent_id")
	public Long getParenetID() {
		return parentID;
	}

	public void setParenetID(Long parenetID) {
		this.parentID = parenetID;
	}

	/**
	 * 是否核心规则包
	 */
	@Column(name = "is_core")
	public boolean isCore() {
		return isCore;
	}

	public void setCore(boolean isCore) {
		this.isCore = isCore;
	}

	/**
	 * 规则包所包含的规则集合，规则包和规则的关系为多对多
	 */
	@ManyToMany(targetEntity = Rule.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinTable(
			name = "lab_bag_rule", 
			joinColumns = { @JoinColumn(name = "bag_id", referencedColumnName = "id") }, 
			inverseJoinColumns = @JoinColumn(name = "rule_id", referencedColumnName = "id"))
	public Set<Rule> getRules() {
		return rules;
	}

	public void setRules(Set<Rule> rules) {
		this.rules = rules;
	}
	
	@Column(name="hospital_id")
	public long getHospitalId(){
		return hospitalId;
	}
	public void setHospitalId(long hospitalId){
		this.hospitalId = hospitalId; 
	}

	@Override
	public String toString() {
		return null;
	}

	@Override
	public boolean equals(Object o) {
		return false;
	}

	@Override
	public int hashCode() {
		return 0;
	}
}
