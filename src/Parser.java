class Parser {
    private String input;
    private int cursor; // indicates current index of the input
    private int L; // length of the input string

    public Parser(String input) {
        this.input = input;
        cursor = 0;
        L = input.length();
    }

    public Expression parse() {
        return parseRelation();
    }

    private Expression parseRelation() { // parser for relations
        Expression result = parseTerm();
        if (getNext() != ']') {
            if (getNext() == '>') {
                cursor++;
                Greater newR = new Greater(result, parseTerm());
                result = newR;
            } else if (getNext() == '<') {
                cursor++;
                Less newR = new Less(result, parseTerm());
                result = newR;
            } else if (getNext() == '=') {
                cursor++;
                Equ newR = new Equ(result, parseTerm());
                result = newR;
            }
        }
        return result;
    }

    private Expression parseTerm() { // parser for terms
        Expression result = parseFactor();
        while (true) {
            if (getNext() != ']') {
                if (getNext() == '+') {
                    cursor++;
                    Sum newR = new Sum(result, parseFactor());
                    result = newR;
                } else if (getNext() == '-') {
                    cursor++;
                    Sub newR = new Sub(result, parseFactor());
                    result = newR;
                } else
                    break;
            } else
                break;
        }
        return result;
    }

    private Expression parseFactor() { // parser for factors
        Expression result = parsePrimary();
        while (true) {
            if (getNext() != ']') {
                if (getNext() == '*') {
                    cursor++;
                    Factor newR = new Factor(result, parsePrimary());
                    result = newR;
                } else
                    break;
            } else
                break;
        }
        return result;
    }

    private Expression parsePrimary() { // parser for primary
        Expression result = null;
        if (getNext() != ']') {
            if (Character.isDigit(getNext()))
                result = parseInteger();
            else if (getNext() == '(') {
                cursor++;
                result = parse();
                int i = cursor;
                while (input.charAt(i) != ')')
                    i++;
                String s1 = input.substring(0, i);
                String s2 = input.substring(i + 1, L);
                input = s1 + s2;
                L--;
            }
        }
        return result;
    }

    private Expression parseInteger() { // returns a number
        Primary result = new Primary(0);
        String valueS = "";
        while (Character.isDigit(getNext()) && getNext() != ']') {
            valueS += getNext();
            cursor++;
        }
        result.value = Integer.valueOf(valueS);
        return result;
    }

    private char getNext() { // returns next symbol
        char next = ']';
        if (cursor < L)
            next = input.charAt(cursor);
        return (next);
    }
}