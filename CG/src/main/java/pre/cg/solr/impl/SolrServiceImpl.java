package pre.cg.solr.impl;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import pre.cg.pojo.Prod;
import pre.cg.solr.SolrService;

import java.io.IOException;


/**
 * @ClassName SolrServiceImpl
 * @Description TODO
 * @Author QQ163
 * @Date 2020/3/23 14:48
 **/
public class SolrServiceImpl implements SolrService {
    @Autowired
    private SolrClient solrClient;

    @Override
    public int insert(Prod prod) throws IOException, SolrServerException {
        return solrClient.addBean(prod).getStatus();
    }
}
