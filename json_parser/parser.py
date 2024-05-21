from lexer import Lexer, tokenRegex

class Parser:
    def __init__(self, tokens):
        self.tokens = tokens
        self.pos = 0

    def parse(self):
        value = self.parse_value()
        if self.pos != len(self.tokens):
            raise SyntaxError("Extra data after JSON value")
        return value

    def parse_value(self):
        if self.match('LBRACE'):
            return self.parse_object()
        elif self.match('LBRACKET'):
            return self.parse_array()
        elif self.match('STRING'):
            return self.consume('STRING')[1][1:-1]  # Strip quotes
        elif self.match('NUMBER'):
            return self.convert_number(self.consume('NUMBER')[1])
        elif self.match('TRUE'):
            self.consume('TRUE')
            return True
        elif self.match('FALSE'):
            self.consume('FALSE')
            return False
        elif self.match('NULL'):
            self.consume('NULL')
            return None
        else:
            raise SyntaxError(f"Unexpected token at position {self.pos}: {self.tokens[self.pos]}")

    def parse_object(self):
        obj = {}
        self.consume('LBRACE')
        if not self.match('RBRACE'):
            while True:
                if not self.match('STRING'):
                    raise SyntaxError(f"Expected string key at position {self.pos}")
                key = self.consume('STRING')[1][1:-1]  # Strip quotes
                if not self.match('COLON'):
                    raise SyntaxError(f"Expected colon after key at position {self.pos}")
                self.consume('COLON')
                value = self.parse_value()
                obj[key] = value
                if self.match('RBRACE'):
                    break
                if not self.match('COMMA'):
                    raise SyntaxError(f"Expected comma or closing brace at position {self.pos}")
                self.consume('COMMA')
        self.consume('RBRACE')
        return obj

    def parse_array(self):
        array = []
        self.consume('LBRACKET')
        if not self.match('RBRACKET'):
            while True:
                array.append(self.parse_value())
                if self.match('RBRACKET'):
                    break
                if not self.match('COMMA'):
                    raise SyntaxError(f"Expected comma or closing bracket at position {self.pos}")
                self.consume('COMMA')
        self.consume('RBRACKET')
        return array

    def match(self, token_type):
        return self.pos < len(self.tokens) and self.tokens[self.pos][0] == token_type

    def consume(self, token_type):
        if self.match(token_type):
            token = self.tokens[self.pos]
            self.pos += 1
            return token
        else:
            raise SyntaxError(f"Expected token {token_type} at position {self.pos} but got {self.tokens[self.pos]}")

    def convert_number(self, number_string):
        try:
            if '.' in number_string or 'e' in number_string or 'E' in number_string:
                return float(number_string)
            else:
                return int(number_string)
        except ValueError:
            raise SyntaxError(f"Invalid number format: {number_string}")

if __name__=='__main__':
    def read_json_file(file_path):
        with open(file_path, 'r') as file:
            return file.read()

    json_file_path = 'test.json'

    try:
        json_string = read_json_file(json_file_path)
        lexer = Lexer(tokenRegex)
        tokens = lexer.tokenize(json_string)
        parser = Parser(tokens)
        parsed_data = parser.parse()
        print("Parsed JSON is valid")
        print(parsed_data)
    except SyntaxError as e:
        print(f"Error parsing JSON: {e}")
