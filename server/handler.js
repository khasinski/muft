var get = function (request) { 
  console.log('get');
  return [
  {
    client: 'a',
    location: [1,1],
    ts: 0
  },
  {
    client: 'b',
    location: [2,2],
    ts: 1
  }
  ]; //TODO: Stub
}

var post = function (request) {
  console.log('post');
  return 0;
}

exports.post = post;
exports.get = get;
