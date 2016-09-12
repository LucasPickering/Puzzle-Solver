import re

RESULT_RGX = re.compile(
	r'(\w+)\.(\w+)\s+(\d+\.\d+)d\s+(\d+)\s+(\w+)\s+(\d+)\s+(\d+\.\d+)[^0-9]+(\d+\.\d+)\s+(.+)')

TEST = {
	'Greedy': {
		(0.2, 50): 5.0,
		(0.2, 100): 7.5,
		(0.1, 50): 10.0
	},
	'PieceType': {
		(0.2, 50): 5.5,
		(0.2, 100): 7.8,
		(0.1, 50): 10.5
	},
	'Rotation': {
		(0.2, 50): 6.0,
		(0.2, 100): 8.5,
		(0.1, 50): 11.0
	}
}

def compare(result_dict):
	diff = {}
	# Populate diff with results for each benchmark
	for result_name in result_dict:
		results = result_dict[result_name]
		for test_params in results:
			if not test_params in diff:
				diff[test_params] = []
			diff[test_params].append((result_name, results[test_params]))

	# Sort the list by time
	for test_params in diff:
		diff[test_params] = sorted(diff[test_params], key=lambda value: value[1])
	return diff

print(compare(TEST))
