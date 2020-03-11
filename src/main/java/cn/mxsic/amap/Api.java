package cn.mxsic.amap;


import org.springframework.http.HttpMethod;

/**
 * Function: Api <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-01-19 11:55:00
 */
public class Api {

        private HttpMethod method;
        private String url;

        public Api(HttpMethod method,
                   String url) {
            this.method = method;
            this.url = url;
        }

        public HttpMethod getMethod() {
            return this.method;
        }

        public String getUrl() {
            return this.url;
        }

        @Override
        public String toString() {
            return "UrlConfiguration.API(method=" + this.getMethod() + ", url=" + this.getUrl() + ")";
        }
}
