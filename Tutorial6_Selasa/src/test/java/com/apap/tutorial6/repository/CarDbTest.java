package com.apap.tutorial6.repository;

import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.apap.tutorial6.model.CarModel;
import com.apap.tutorial6.model.DealerModel;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CarDbTest {
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private CarDb carDb;
	
	@Test
	public void whenFindByType_theReturnCar() {
		DealerModel dealerModel = new DealerModel();
		dealerModel.setNama("Rendezyous");
		dealerModel.setAlamat("Jalan");
		dealerModel.setNoTelp("0878");
		entityManager.persist(dealerModel);
		entityManager.flush();
		
		CarModel carModel = new CarModel();
		carModel.setBrand("toyota");
		carModel.setType("jazz");
		carModel.setPrice(new Long("100000000"));
		carModel.setAmount(20);
		carModel.setDealer(dealerModel);
		entityManager.persist(carModel);
		entityManager.flush();
		
		//when
		Optional<CarModel> found = carDb.findByType(carModel.getType());
		
		assertThat(found.get(), Matchers.notNullValue()); //check if not null
		assertThat(found.get(), Matchers.equalTo(carModel)); //check if same
	}
}
