goog.provide('example.Main');

goog.require('example.Hello');

/**
 * @param {string} id
 */
example.Main.run = function(id) {
	new example.Hello(id);
}

goog.exportSymbol('example.Main.run', example.Main.run);


