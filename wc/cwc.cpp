#include <bits/stdc++.h>
using namespace std;

int countBytes(istream &input) {
    string data((istreambuf_iterator<char>(input)), istreambuf_iterator<char>());
    return data.size();
}

int countLines(istream &input) {
    int count = 0;
    string line;
    while (getline(input, line)) {
        count++;
    }

    input.seekg(-1, ios::end); // Go to the last character
    char lastChar;
    input.get(lastChar);
    if (lastChar != '\n' && input.tellg() != -1) {
        count--;  // Adjust count if the last character is not a newline
    }

    input.clear();
    input.seekg(0, ios::beg); // Reset stream position

    return count;
}

int countWords(istream &input) {
    int word_count = 0;
    string word;
    while (input >> word) {
        word_count++;
    }
    input.clear();
    input.seekg(0, ios::beg); // Reset stream position
    return word_count;
}

int countChars(istream &input) {
    string data((istreambuf_iterator<char>(input)), istreambuf_iterator<char>());
    // Setup UTF-8 converter
    wstring_convert<codecvt_utf8<char32_t>, char32_t> converter;
    // Convert UTF-8 string to UTF-32 (each char32_t represents one Unicode character)
    u32string utf32string = converter.from_bytes(data);
    // The length of the UTF-32 string gives the number of UTF-8 characters
    return utf32string.length();
}

int main(int argc, char *argv[]) {
    if (argc < 2) {
        cerr << "Usage: " << argv[0] << " [-c|-l|-w|-m] [<filename>]" << endl;
        cerr << "If filename is not provided, input is read from stdin." << endl;
        return 1; // Exit if the minimum arguments are not provided
    }

    int result = 0;
    string filename;

    // Check if filename is provided
    if (argc == 3) {
        filename = argv[2];
    }

    // Open file if provided, otherwise read from stdin
    ifstream file;
    if (!filename.empty()) {
        file.open(filename);
        if (!file.is_open()) {
            cerr << "Error opening file: " << filename << endl;
            return 1;
        }
    } else {
        file.copyfmt(cin); // Save current state of cin
        file.clear(); // Clear any errors
        file.basic_ios<char>::rdbuf(cin.rdbuf()); // Assign cin buffer to file
    }

    string flag = argv[1];

    if (flag == "-c") {
        result = countBytes(file);
    } else if (flag == "-l") {
        result = countLines(file);
    } else if (flag == "-w") {
        result = countWords(file);
    } else if (flag == "-m") {
        result = countChars(file);
    } else {
        cerr << "Invalid flag. Usage: " << argv[0] << " [-c|-l|-w|-m] [<filename>]" << endl;
        return 1;
    }

    cout << result << endl;

    return 0;
}