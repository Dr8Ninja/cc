from django import forms

class JSONUploadForm(forms.Form):
    json_file = forms.FileField(label='Upload JSON File', required=False)
    json_text = forms.CharField(widget=forms.Textarea, label='Or Enter JSON Text', required=False)
