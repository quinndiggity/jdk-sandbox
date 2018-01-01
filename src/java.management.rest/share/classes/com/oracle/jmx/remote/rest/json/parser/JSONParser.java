/* Generated By:JavaCC: Do not edit this line. JSONParser.java */
package com.oracle.jmx.remote.rest.json.parser;

import java.io.StringReader;
import com.oracle.jmx.remote.rest.json.*;

public class JSONParser implements JSONParserConstants {

    public JSONParser(String input) {
        this(new StringReader(input));
    }

    public JSONElement parse() throws ParseException {
        return jsonValue();
    }

  final public JSONElement jsonValue() throws ParseException {
        JSONElement x;
    switch (jj_nt.kind) {
    case 16:
      x = object();
      break;
    case 20:
      x = list();
      break;
    case QUOTED_STRING:
      x = string();
      break;
    case INTEGER_LITERAL:
    case FLOATING_POINT_LITERAL:
      x = number();
      break;
    case BOOL_LITERAL:
      x = boolVal();
      break;
    case NULL:
      x = nullVal();
      break;
    default:
      jj_la1[0] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
          {if (true) return x;}
    throw new Error("Missing return statement in function");
  }

  final public JSONObject object() throws ParseException {
        final JSONObject jobject = new JSONObject();
        JSONPrimitive key;
        JSONElement value;
    jj_consume_token(16);
    key = string();
    jj_consume_token(17);
    value = jsonValue();
          jobject.put((String)key.getValue(), value);
    label_1:
    while (true) {
      switch (jj_nt.kind) {
      case 18:
        ;
        break;
      default:
        jj_la1[1] = jj_gen;
        break label_1;
      }
      jj_consume_token(18);
      key = string();
      jj_consume_token(17);
      value = jsonValue();
                  jobject.put((String)key.getValue(), value);
    }
    jj_consume_token(19);
      {if (true) return jobject;}
    throw new Error("Missing return statement in function");
  }

  final public JSONArray list() throws ParseException {
    final JSONArray jarray = new JSONArray();
    JSONElement value;
    jj_consume_token(20);
    value = jsonValue();
          jarray.add(value);
    label_2:
    while (true) {
      switch (jj_nt.kind) {
      case 18:
        ;
        break;
      default:
        jj_la1[2] = jj_gen;
        break label_2;
      }
      jj_consume_token(18);
      value = jsonValue();
              jarray.add(value);
    }
    jj_consume_token(21);
      {if (true) return jarray;}
    throw new Error("Missing return statement in function");
  }

  final public JSONPrimitive nullVal() throws ParseException {
    jj_consume_token(NULL);
      {if (true) return null;}
    throw new Error("Missing return statement in function");
  }

  final public JSONPrimitive boolVal() throws ParseException {
    jj_consume_token(BOOL_LITERAL);
     {if (true) return new JSONPrimitive(Boolean.parseBoolean(token.image));}
    throw new Error("Missing return statement in function");
  }

  final public JSONPrimitive number() throws ParseException {
    Token t;
    switch (jj_nt.kind) {
    case INTEGER_LITERAL:
      t = jj_consume_token(INTEGER_LITERAL);
                             {if (true) return new JSONPrimitive(Long.parseLong(t.image));}
      break;
    case FLOATING_POINT_LITERAL:
      t = jj_consume_token(FLOATING_POINT_LITERAL);
                                      {if (true) return new JSONPrimitive(Double.parseDouble(t.image));}
      break;
    default:
      jj_la1[3] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public JSONPrimitive string() throws ParseException {
    Token t;
    t = jj_consume_token(QUOTED_STRING);
      {if (true) return new JSONPrimitive(t.image.substring(1,t.image.length()-1));}
    throw new Error("Missing return statement in function");
  }

  /** Generated Token Manager. */
  public JSONParserTokenManager token_source;
  JavaCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_gen;
  final private int[] jj_la1 = new int[4];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x11e180,0x40000,0x40000,0x180,};
   }

  /** Constructor with InputStream. */
  public JSONParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public JSONParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new JavaCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new JSONParserTokenManager(jj_input_stream);
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public JSONParser(java.io.Reader stream) {
    jj_input_stream = new JavaCharStream(stream, 1, 1);
    token_source = new JSONParserTokenManager(jj_input_stream);
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public JSONParser(JSONParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(JSONParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken = token;
    if ((token = jj_nt).next != null) jj_nt = jj_nt.next;
    else jj_nt = jj_nt.next = token_source.getNextToken();
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    jj_nt = token;
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if ((token = jj_nt).next != null) jj_nt = jj_nt.next;
    else jj_nt = jj_nt.next = token_source.getNextToken();
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[22];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 4; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 22; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
