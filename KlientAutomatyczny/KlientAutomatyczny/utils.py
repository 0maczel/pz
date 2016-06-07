import argparse


def check_limit(limit):
    parsed_integer_limit = int(limit)
    if parsed_integer_limit < 1:
         raise argparse.ArgumentTypeError("%s musi byc wiekszy od 0" % limit)
    return parsed_integer_limit