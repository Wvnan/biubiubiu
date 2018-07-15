package com.itcast.store.dao1;

import java.sql.SQLException;
import java.util.List;

import com.itcast.store.domain.Product;



/**
 * 商品DAO的接口
 * @author admin
 *
 */
public interface ProductDao {

	List<Product> findByHot()throws SQLException ;

	List<Product> findByNew()throws SQLException ;

	Integer findCountByCid(String cid)throws SQLException ;

	List<Product> findPageByCid(String cid, int begin, Integer pageSize)throws SQLException ;

	Product findByPid(String pid)throws SQLException ;

	Integer findCount() throws SQLException;

	List<Product> findByPage(int begin, Integer pageSize) throws SQLException;

	void save(Product product) throws Exception;
	
}
