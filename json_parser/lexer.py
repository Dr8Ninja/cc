import re

# Token definitions
tokenRegex = {
    'LBRACE': r'{',
    'RBRACE': r'}',
    'LBRACKET': r'\[',
    'RBRACKET': r'\]',
    'COMMA': r',',
    'COLON': r':',
    'STRING': r'"(\\.|[^"\\])*"',
    'NUMBER': r'-?\d+(\.\d+)?([eE][+-]?\d+)?',
    'TRUE': r'true',
    'FALSE': r'false',
    'NULL': r'null',
    'WHITESPACE': r'\s+',
}

class Lexer:
    def __init__(self, rules):
        self.rules = [(name, re.compile(pattern)) for name, pattern in rules.items()]

    def tokenize(self, text):
        pos = 0
        tokens = []

        while pos < len(text):
            match = None
            for token_name, token_regex in self.rules:
                match = token_regex.match(text, pos)
                if match:
                    if token_name != 'WHITESPACE':  # Skip whitespace
                        token_value = match.group(0)
                        tokens.append((token_name, token_value))
                    pos = match.end(0)
                    break

            if not match:
                raise SyntaxError(f"Illegal character at position {pos}: '{text[pos]}'")

        return tokens
