package com.javausergroupcr.springdata.app.models.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.Data;

@Data
@Entity
@Table(name = "invoices")
public class Invoice implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Client client;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "invoice_id")
	private List<InvoiceItem> items;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "invoice_id")
	@Where(clause = "enabled = true")
	@OrderBy("createdAt DESC")
	private List<Payment> payments;

	private boolean enabled;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by")
	private DBUser createdBy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "updated_by")
	private DBUser updatedBy;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	@PrePersist
	public void prePersist() {
		createdAt = LocalDateTime.now();
	}

	public void addItem(InvoiceItem item) {
		this.items.add(item);
	}

	public void addPayment(Payment payment) {
		this.payments.add(payment);
	}

	public double getTotal() {
		return items.stream().mapToDouble(i -> i.getQuantity() * i.getPrice()).sum();
	}

	public double getPaid() {
		return payments.stream().mapToDouble(p -> p.getAmount()).sum();
	}

	private static final long serialVersionUID = 1L;

}
