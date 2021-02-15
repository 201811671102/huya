package pre.cg.solr;

import org.apache.solr.client.solrj.SolrServerException;
import pre.cg.pojo.Prod;

import java.io.IOException;

public interface SolrService {
    /*solr添加*/
    int insert(Prod prod) throws IOException, SolrServerException;
}
