package com.example.datesource.demo;

import com.example.datesource.demo.repository.OrderRepository;
import com.example.datesource.demo.repository.impl.OrderRepositoryImpl;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.apache.shardingsphere.transaction.core.TransactionTypeHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;
import org.springframework.context.ApplicationContext;
import com.example.datesource.demo.model.Order;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

@SpringBootApplication(exclude = JtaAutoConfiguration.class)
public class DemoApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(DemoApplication.class, args);
		DataSource ds = context.getBean(DataSource.class);
		OrderRepository repo = new OrderRepositoryImpl(ds);
		try {
			// 测试CUID
			cuid(repo);

			// 测试XA事务
			tryTransaction(ds);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void cuid(OrderRepository repo) throws Exception{
		Order o1 = new Order();
		o1.setUserId(1);
		o1.setStatus("init");
		repo.insert(o1);
		repo.delete(o1.getOrderId());
	}

	public static void tryTransaction(DataSource ds) throws Exception{
		TransactionTypeHolder.set(TransactionType.XA); // 支持 TransactionType.LOCAL, TransactionType.XA, TransactionType.BASE
		Connection conn = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("INSERT INTO t_order (user_id, status) VALUES (?, ?)");
			ps.setObject(1, 1000);
			ps.setObject(2, "init");
			ps.executeUpdate();

			ps = conn.prepareStatement("INSERT INTO t_order (user_id, status) VALUES (?, ?)");
			ps.setObject(1, 2000);
			ps.setObject(2, "update");
			ps.executeUpdate();
			// 假设第二个update没成功发生异常
			if(true)
				throw new RuntimeException("mock exception");

			conn.commit();
		}catch(RuntimeException e){
			if(conn != null)
				conn.rollback();
		}finally {
			TransactionTypeHolder.clear();
		}
	}
}
