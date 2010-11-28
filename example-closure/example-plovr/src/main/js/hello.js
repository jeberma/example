goog.require('goog.dom');

goog.provide('example.Hello');

/**
 * @param {string|Element} id
 * @constructor
 */
example.Hello = function(id) {
	var newHeader = goog.dom.createDom('h1',{'style': 'background-color:#EEE'}, 'Hello World!');
	goog.dom.appendChild(goog.dom.getElement(id), newHeader);
}