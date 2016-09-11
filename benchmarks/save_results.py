import re
from http import client

ES_HOST = 'localhost:9200'
DOC_LOCATION = '/speed/results/{}'
RESULT_RGX = re.compile(
	r'(\w+)\.(\w+)\s+(\d+\.\d+)d\s+(\d+)\s+(\w+)\s+(\d+)\s+(\d+\.\d+)[^0-9]+(\d+\.\d+)\s+(.+)')

def save_results(results_file):
	results = {}
	results['date'] = 'date'
	results['branch'] = 'branch'
	results['commit'] = 'commit'
	for line in open(results_file).read().splitlines():
		match = RESULT_RGX.match(line)
		if match:
			groups = match.groups()

			class_results = {}
			results[groups[0]] = class_results

			method_results = {}
			class_results[groups[1]] = method_results

			parameters = {}
			method_results['parameters'] = parameters
			parameters['simplicity'] = float(groups[2])
			parameters['size'] = int(groups[3])

			method_results['iterations'] = int(groups[5])
			method_results['score'] = float(groups[6])
			method_results['error'] = float(groups[7]) / method_results['score'] * 100
	return results

def send_to_es(results):
	connection = client.HTTPConnection(ES_HOST)
	connection.request('PUT', DOC_LOCATION.format(results['date']), results)
	print(connection.getresponse().read())
	connection.close()

results = save_results('results/2016-09-10_15.43.19.txt')
send_to_es(results)
