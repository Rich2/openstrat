/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** HTML table element. */
case class HtmlTable(val contents: RArr[HtmlRow], val attribs: RArr[XmlAtt] = RArr()) extends HtmlMultiLine
{ override def tag: String = "table"
}

object HtmlTable
{ /** Factory apply method for creating HTML table elements. */
  def apply(contents: HtmlRow*):  HtmlTable = new HtmlTable(contents.toArr)
  def width100(contents: HtmlRow*):  HtmlTable = new HtmlTable(contents.toArr, RArr(WidthAtt("100%")))
}

/** HTML TR table row element class. */
trait HtmlRow extends HtmlMultiLine
{ override def tag: String = "tr"
}

object HtmlRow
{ /** Convenience method for creating an HTML row element of 2 cells from 2 [[String]]s. */
  def strs2(str1: String, str2: String): HtmlRow = HtmlRowData(RArr(HtmlTd(str1), HtmlTd(str2)))

  /** Convenience method for creating an HTML row element of 3 cells from 3 [[String]]s. */
  def strs3(str1: String, str2: String, str3: String): HtmlRow = HtmlRowData(RArr(HtmlTd(str1), HtmlTd(str2), HtmlTd(str3)))

  /** Convenience method for creating an HTML row element of 4 cells from 4 [[String]]s. */
  def strs4(str1: String, str2: String, str3: String, str4: String): HtmlRow = HtmlRowData(RArr(HtmlTd(str1), HtmlTd(str2), HtmlTd(str3), HtmlTd(str4)))
}

/** HTML TR table row element class. */
case class HtmlRowHead(val contents: RArr[HtmlTh], val attribs: RArr[XmlAtt] = RArr()) extends HtmlRow

object HtmlRowHead
{ /** Convenience method for creating an HTML row element of 2 cells from 2 [[String]]s. */
  def strs2(str1: String, str2: String): HtmlRowHead = HtmlRowHead(RArr(HtmlTh(str1), HtmlTh(str2)))

  /** Convenience method for creating an HTML row element of 3 cells from 3 [[String]]s. */
  def strs3(str1: String, str2: String, str3: String): HtmlRowHead = HtmlRowHead(RArr(HtmlTh(str1), HtmlTh(str2), HtmlTh(str3)))

  /** Convenience method for creating an HTML row element of 4 cells from 4 [[String]]s. */
  def strs4(str1: String, str2: String, str3: String, str4: String): HtmlRowHead =
    HtmlRowHead(RArr(HtmlTh(str1), HtmlTh(str2), HtmlTh(str3), HtmlTh(str4)))
}
/** HTML TR table row element class. */
case class HtmlRowData(val contents: RArr[HtmlTd], val attribs: RArr[XmlAtt] = RArr()) extends HtmlRow

object HtmlRowData
{ /** Convenience method for creating an HTML row element of 2 cells from 2 [[String]]s. */
  def strs2(str1: String, str2: String): HtmlRowData = HtmlRowData(RArr(HtmlTd(str1), HtmlTd(str2)))

  /** Convenience method for creating an HTML row element of 3 cells from 3 [[String]]s. */
  def strs3(str1: String, str2: String, str3: String): HtmlRowData = HtmlRowData(RArr(HtmlTd(str1), HtmlTd(str2), HtmlTd(str3)))

  /** Convenience method for creating an HTML row element of 4 cells from 4 [[String]]s. */
  def strs4(str1: String, str2: String, str3: String, str4: String): HtmlRowData = HtmlRowData(RArr(HtmlTd(str1), HtmlTd(str2), HtmlTd(str3), HtmlTd(str4)))
}


trait HtmlCell extends HtmlInline

/** HTML TH table header cell element. */
case class HtmlTh(contents: RArr[XCon], attribs: RArr[XmlAtt]) extends HtmlCell
{ override def tag: String = "th"
}

object HtmlTh
{ /** Factory apply method to construct HTML TH table header cell element form a simple [[String]]. */
  def apply(str: String): HtmlTh = new HtmlTh(RArr(str.xCon), RArr())
}

/** HTML TD table data cell element. */
case class HtmlTd(contents: RArr[XCon], attribs: RArr[XmlAtt]) extends HtmlCell
{ override def tag: String = "td"
}

object HtmlTd
{ /** Factory apply method to construct HTML TD table data cell element form a simple [[String]]. */
  def apply(str: String) = new HtmlTd(RArr(str.xCon), RArr())
}