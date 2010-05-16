import grails.test.*

class QuoteServiceIntegrationTestTests extends GrailsUnitTestCase {

	def quoteService

    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testStaticQuote() {
		def staticQuote = quoteService.getStaticQuote()
		assertEquals('Anonymous', staticQuote.author)
		assertEquals('Real Programmers don\'t eat quiche', staticQuote.content)
    }
}
