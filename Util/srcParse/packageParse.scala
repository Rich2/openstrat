/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** This is the RSON package. Readable Succinct Object Notation. It could be just as accurately be described as Readable Succinct Data Notation, but
 *  that wouldn't scan as well and indicate it as a better replacement for JSON. RSON uses the standard semicolon separated statements combined with
 *  multilevel bracket hierarchy syntax familiar from C, C++, Java and JavaScript. Its main innovations over JSON and XML are allowing statements to
 *  be partitioned into comma delineated clauses, the empty statement, the empty clause and type inference. It uses a context free operator
 *  precedence hierarchy, with the exception of allowing the 4 ( - + ~ !) operator to be used as prefix operators. Beyond this it generally follows
 *  Scala syntax where this doesn't conflict with the preceding rules. Unlike Scala statements must finish with a semicolon unless it is the last
 *  statement of a file or a bracket block. Allowing statements to end with a newline introduces horrendous ambiguities unless one is willing to go
 *  completely down the significant whitespace route and I'm not sure if that could work even work for such a general purpose basic syntax.  
 *  
 *  The above allows it to combine a high level of human readability, succinctness, non-programmer / non-expert write-ability, programmer flexibility
 *  and composability and fast-parsing. The initial motivating use case was strategy games, which require huge amounts of modable date files. XML and
 *  JSON, the current defaults are simply not fit for purpose. RSON aims to break down the walls between game player, game modder, professional game
 *  artist, professional game scripter, professional imperative programming wizards in languages such as C / C++ and Rust and professional functional
 *  programming wizards in languages such as Scala, Haskell and Idris. */
package object pParse
{ /** type alias for Arr[Token] .*/
  type Tokens = RArr[Token]

  /** Returns an EMon of a sequence of Statements from a file. This uses the fromString method. Non fatal exceptions or if the file doesn't exist
   *   will be returned as errors. */
  def srcToEStatements(input: Array[Char], inputSourceName: String): EArr[Statement] =
    plex.lexSrc(input, inputSourceName).flatMap(tokensToStatements(_))

  /** Returns an EMon of a sequence of Statements from a String. */
  def stringToStatements(input: String): EArr[Statement] = stringToTokens(input).flatMap(tokensToStatements(_))

  /** Max numbers for long and hexidecimal formats needs to be implemented */
  def stringToTokens(srcStr: String): EArr[Token] = plex.lexSrc(srcStr.toCharArray, "String")

  def isOperator(char: Char): Boolean = char match
  { case '+' | '-' | '*' | '/' | '=' | ':'  => true
    case _ => false
  }

  /** Tries to parse a sequence of [[Token]]s to [[Statement]]s. */
  def tokensToStatements(tokens: RArr[Token]): EArr[Statement] = pAST.parse1BlockStructure(tokens).flatMap{ g => blockMemsToStatements(g)}

  /** Tries to parse a sequence of block members [[BlockMem]]s to a sequence of [[Statement]]s. */
  def blockMemsToStatements(inp: RArr[BlockMem]): EArr[Statement] = pAST.parse3Statements(inp).map{
    case StringStatements(sts) => sts
    case e => RArr(StatementNoneEmpty(e, None))
  }

  /** Tries to parse a sequence of tokens to an expression [[Expr]]. */
  def tokensToExpr(tokens: RArr[Token]): EMon[Expr] = pAST.parse1BlockStructure(tokens).flatMap{ g => pAST.parse3Statements(g)}
}