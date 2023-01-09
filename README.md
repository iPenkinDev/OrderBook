# OrderBook

Imagine that you are in a market which only trades one type of item (e.g. tomatoes of equal quality; generally known as "shares"). There will be a certain amount of tomatoes (shares) being offered at certain prices. Also, there will be people willing to buy at a certain price. Imagine that everyone who could buy/sell at an acceptable price(limit price) immediately does that and leaves the market. This way (most of the time), nobody can perform a trade right now and everyone has to wait until something changes (e.g. someone reconsiders "acceptable" price, or a new person appears). Those "limit orders" (people willing to buy/sell in certain quantities) are the limit order book. In some cases people are willing to buy/sell at any price (that's called a "market order"), such a person is always going to perform the trade and then leaves the market.

Input/Output Data Format Input file Each line in the file can be one of the following:

Updates to the limit order book in the following format: u,,,bid - set bid size at to u,,,ask - set ask size at to Queries in the following format: q,best_bid - print best bid price and size q,best_ask - print best ask price and size q,size, - print size at specified price (bid, ask or spread). And market orders in the following format: o,buy, - removes shares out of asks, most cheap ones. o,sell, - removes shares out of bids, most expensive ones In case of a buy order this is similar to going to a market (assuming that you want to buy similar items there, and that all instances have identical quality, so price is the only factor) - you buy units at the cheapest price available.

Queries, market orders, and limit order book updates are in arbitrary sequence. Each line in the file is either one of the three and ends with a UNIX newline character - \n.

Input values range: Price - 1...109 Size - 0...108

Example of input file: u,9,1,bid u,11,5,ask q,best_bid u,10,2,bid q,best_bid o,sell,1 q,size,10 u,9,0,bid u,11,0,ask Output file Example of output file (for this input file):

9,1 10,2 1
