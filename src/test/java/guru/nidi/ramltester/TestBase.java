package guru.nidi.ramltester;

import org.hamcrest.Matcher;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.StringContains;
import org.hamcrest.core.StringEndsWith;
import org.hamcrest.core.StringStartsWith;
import org.raml.model.Raml;
import org.raml.parser.visitor.RamlDocumentBuilder;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 *
 */
public class TestBase {
    protected Raml raml(String res) {
        return new RamlDocumentBuilder().build(getClass().getResourceAsStream(res), getClass().getName().replace('.', '/'));
    }

    protected MockHttpServletRequest get(String url) {
        return MockMvcRequestBuilders.get(url).buildRequest(new MockServletContext());
    }

    protected MockHttpServletRequest post(String url) {
        return MockMvcRequestBuilders.post(url).buildRequest(new MockServletContext());
    }

    protected Matcher<String> startsWith(String s) {
        return new StringStartsWith(s);
    }

    protected Matcher<String> contains(String s) {
        return new StringContains(s);
    }

    protected Matcher<String> endsWith(String s) {
        return new StringEndsWith(s);
    }

    @SafeVarargs
    protected final <T> Matcher<T> allOf(Matcher<? super T>... matcher) {
        return new AllOf<>(Arrays.asList(matcher));
    }

    protected MockHttpServletResponse jsonResponse(int code, String json, String contentType) throws UnsupportedEncodingException {
        final MockHttpServletResponse response = new MockHttpServletResponse();
        response.setStatus(code);
        response.setContentType(contentType);
        response.getWriter().print(json);
        return response;
    }

    protected MockHttpServletResponse jsonResponse(int code, String json) throws UnsupportedEncodingException {
        return jsonResponse(code, json, "application/json");
    }
}
