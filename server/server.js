var http = require('http');
var url = require('url');

var handler = require('./handler');

var handle = {};
handle['/'] = handler.handle;


function onRequest(request, response) {
  response.writeHead(200, {"Content-Type": "application/json"}); 
  var pathname = url.parse(request.url).pathname;
  if(undefined != handle[pathname]) {
  	response.write(JSON.stringify(handle[pathname](request)));
  }
  else
  	response.write(JSON.stringify({'error':'404'}));
  response.end();
}

http.createServer(onRequest).listen(8888);
