/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** Citation attribute for XML / HTML. */
case class CiteAtt(valueStr: String) extends XAttSimple
{ override def name: String = "cite"
}

/** HTML blockquote element. */
trait HtmlBlockQuote extends HtmlTagLines
{ def citeStr: String
  override def tagName: String = "blockquote"
  override def attribs: RArr[XAtt] = RArr(CiteAtt(citeStr))

}

object HtmlBlockQuote
{ /** Factory apply method for HTML blockquote element. */
  def apply(citeStr: String, quoteStr: String): HtmlBlockQuote = HtmlBlockQuoteGen(citeStr, quoteStr)

  /** Implementation class for the general case of [[HtmlBlockQuote]] element. */
  case class HtmlBlockQuoteGen(citeStr: String, quoteStr: String) extends HtmlBlockQuote
  {
    override def contents: RArr[XCon] = RArr(quoteStr)
  }
}

trait BlockQuoteNoted extends HtmlBlockQuote
{
  def noteNum: Int
}

class BlockQuoteAnchored(val quoteBody: String, val noteNum: Int, val link: String, linkLabelIn: String = "") extends BlockQuoteNoted
{ override val citeStr: String = linkLabelIn.emptyMap(link)
  override def contents: RArr[XCon] =
  { val sup =  HtmlSup.atts(HtmlA(s"#note$noteNum", s"fn$noteNum"))(IdAtt(s"#src$noteNum"))
    RArr(quoteBody, sup)
  }
}

/** HTML short quote element. */
case class HtmlQ(valueStr: String, citeStr: String = "") extends HtmlInline
{ override def tagName: String = "q"
  override def attribs: RArr[XAtt] = ife(citeStr == "", RArr(), RArr(CiteAtt(citeStr)))
  override def contents: RArr[XCon] = RArr(valueStr)
}

case class HtmlNote(num: Int, contextStr: String) extends HtmlP
{ override def attribs: RArr[XAtt] = RArr(IdAtt(s"note$num"))
  override def contents: RArr[XCon] = RArr(contextStr)
}

class NoteTaker
{
  case class Note(num: Int, citeStr: String)
  val acc: RBuff[Note] = RBuff()
  def len: Int = acc.length
  def addNote(num: Int, citeStr: String): Unit = acc.grow(Note(num, citeStr))

  def supScript(contextStr: String): HtmlSup =
  { val num = len + 1
    acc.grow(Note(num, contextStr))
    HtmlSup(HtmlA(s"#note$num", s"fn$num"))
  }

  def noteSect: HtmlSection =
  { val notes: RArr[HtmlP] = acc.map{nt => HtmlP.id(s"note${nt.num.str}", HtmlB(s"${nt.num.str}."), nt.citeStr) }
    HtmlSection(HtmlH2("Notes") %: notes)
  }
}

object NoteTaker
{
  def apply(): NoteTaker = new NoteTaker
}