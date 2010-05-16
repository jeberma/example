class QuoteService {

    boolean transactional = false 
	
	def getStaticQuote() {
		return new Quote(author:'Anonymous',
			content: 'Real Programmers don\'t eat quiche')
	}

	def getRandomQuote() {
		def allQuotes = Quote.list()
		def randomQuote = null
		if(allQuotes.size() > 0) {
			def randomIdx = new Random().nextInt(allQuotes.size())
			randomQuote = allQuotes[randomIdx]
		} else {
			randomQuote = getStaticQuote()
		}
		return randomQuote
	} 
 
}
