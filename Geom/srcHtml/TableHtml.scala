/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** HTML table element. */
case class TableHtml(val contents: RArr[RowHtml], val attribs: RArr[XAtt] = RArr()) extends HtmlTagLines
{ override def tagName: String = "table"
}

object TableHtml
{ /** Factory apply method for creating HTML table elements. */
  def apply(contents: RowHtml*):  TableHtml = new TableHtml(contents.toArr)
  def width100(contents: RowHtml*):  TableHtml = new TableHtml(contents.toArr, RArr(WidthAtt("100%")))
}

/** HTML TR table row element class. */
trait RowHtml extends HtmlTagLines
{ override def tagName: String = "tr"
}

object RowHtml
{ /** Convenience method for creating an HTML row element of 2 cells from 2 [[String]]s. */
  def strs2(str1: String, str2: String): RowHtml = RowDataHtml(RArr(TdHtml(str1), TdHtml(str2)))

  /** Convenience method for creating an HTML row element of 3 cells from 3 [[String]]s. */
  def strs3(str1: String, str2: String, str3: String): RowHtml = RowDataHtml(RArr(TdHtml(str1), TdHtml(str2), TdHtml(str3)))

  /** Convenience method for creating an HTML row element of 4 cells from 4 [[String]]s. */
  def strs4(str1: String, str2: String, str3: String, str4: String): RowHtml = RowDataHtml(RArr(TdHtml(str1), TdHtml(str2), TdHtml(str3), TdHtml(str4)))
}

/** HTML TR table row element class. */
case class RowHeadHtml(val contents: RArr[ThHtml], val attribs: RArr[XAtt] = RArr()) extends RowHtml

object RowHeadHtml
{ /** Convenience method for creating an HTML row element of 2 cells from 2 [[String]]s. */
  def strs2(str1: String, str2: String): RowHeadHtml = RowHeadHtml(RArr(ThHtml(str1), ThHtml(str2)))

  /** Convenience method for creating an HTML row element of 3 cells from 3 [[String]]s. */
  def strs3(str1: String, str2: String, str3: String): RowHeadHtml = RowHeadHtml(RArr(ThHtml(str1), ThHtml(str2), ThHtml(str3)))

  /** Convenience method for creating an HTML row element of 4 cells from 4 [[String]]s. */
  def strs4(str1: String, str2: String, str3: String, str4: String): RowHeadHtml =
    RowHeadHtml(RArr(ThHtml(str1), ThHtml(str2), ThHtml(str3), ThHtml(str4)))
}
/** HTML TR table row element class. */
case class RowDataHtml(val contents: RArr[TdHtml], val attribs: RArr[XAtt] = RArr()) extends RowHtml

object RowDataHtml
{ /** Convenience method for creating an HTML row element of 2 cells from 2 [[String]]s. */
  def strs2(str1: String, str2: String): RowDataHtml = RowDataHtml(RArr(TdHtml(str1), TdHtml(str2)))

  /** Convenience method for creating an HTML row element of 3 cells from 3 [[String]]s. */
  def strs3(str1: String, str2: String, str3: String): RowDataHtml = RowDataHtml(RArr(TdHtml(str1), TdHtml(str2), TdHtml(str3)))

  /** Convenience method for creating an HTML row element of 4 cells from 4 [[String]]s. */
  def strs4(str1: String, str2: String, str3: String, str4: String): RowDataHtml = RowDataHtml(RArr(TdHtml(str1), TdHtml(str2), TdHtml(str3), TdHtml(str4)))
}


trait CellHtml extends HtmlOwnLine

/** HTML TH table header cell element. */
case class ThHtml(contents: RArr[XCon], attribs: RArr[XAtt]) extends CellHtml
{ override def tagName: String = "th"
}

object ThHtml
{ /** Factory apply method to construct HTML TH table header cell element form a simple [[String]]. */
  def apply(str: String): ThHtml = new ThHtml(RArr(str), RArr())
}

/** HTML TD table data cell element. */
case class TdHtml(contents: RArr[XCon], attribs: RArr[XAtt]) extends CellHtml
{ override def tagName: String = "td"
}

object TdHtml
{ /** Factory apply method to construct HTML TD table data cell element form a simple [[String]]. */
  def apply(str: String) = new TdHtml(RArr(str), RArr())
}