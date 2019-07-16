package org.baranov.germes.app.model.entity.person;

import org.baranov.germes.app.model.entity.base.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity that encapsulates user of the application
 */
@Table(name = "ACCOUNT")
@Entity
public class Account extends AbstractEntity {

}
