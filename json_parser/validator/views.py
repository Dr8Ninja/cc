from django.shortcuts import render
from .forms import JSONUploadForm

from lexer import Lexer, tokenRegex
from parser import Parser

def validate_json(json_content):
    lexer = Lexer(tokenRegex)
    try:
        tokens = lexer.tokenize(json_content)
        parser = Parser(tokens)
        parser.parse()
        return "Valid JSON"
    except SyntaxError as e:
        return f"Invalid JSON: {e}"

def index(request):
    if request.method == 'POST':
        form = JSONUploadForm(request.POST, request.FILES)
        result = None
        if form.is_valid():
            json_file = form.cleaned_data.get('json_file')
            json_text = form.cleaned_data.get('json_text')
            if json_file:
                json_content = json_file.read().decode('utf-8')
                result = validate_json(json_content)
            elif json_text:
                result = validate_json(json_text)
            else:
                result = "No JSON input provided."
        return render(request, 'validator/index.html', {'form': form, 'result': result})
    else:
        form = JSONUploadForm()
    return render(request, 'validator/index.html', {'form': form})
