/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*

object TestPage1 extends HtmlPageFile
{ override def fileNameStem: String = "TestPage1"
  override def titleStr: String = "Endnotes Test page."

  override def body: HtmlBody = HtmlBody(h1, p1, p2, p3, n1)

  val h1 = HtmlH1("This is a test page for end notes.")

  val p1: HtmlP = HtmlP.id("para1", "I'm going to link this footnote marker", HtmlSup(HtmlA("#note1", "fn1")), """to the footnote at the bottom of the intro
  |text.
  |<br>Once more unto the breach, dear friends, once more;
  |<br>Or close the wall up with our English dead.
  |<br>In peace there's nothing so becomes a man
  |<br>As modest stillness and humility:
  |<br>But when the blast of war blows in our ears,
  |<br>Then imitate the action of the tiger;
  |<br>Stiffen the sinews, summon up the blood,
  |<br>Disguise fair nature with hard-favour'd rage;
  |<br>Then lend the eye a terrible aspect;
  |<br>Let pry through the portage of the head
  |<br>Like the brass cannon; let the brow o'erwhelm it
  |<br>As fearfully as doth a galled rock
  |<br>O'erhang and jutty his confounded base,
  |<br>Swill'd with the wild and wasteful ocean.
  |<br>Now set the teeth and stretch the nostril wide,
  |<br>Hold hard the breath and bend up every spirit
  |<br>To his full height. On, on, you noblest English.
  |<br>Whose blood is fet from fathers of war-proof!
  |<br>Fathers that, like so many Alexanders,
  |<br>Have in these parts from morn till even fought
  |<br>And sheathed their swords for lack of argument:
  |<br>Dishonour not your mothers; now attest
  |<br>That those whom you call'd fathers did beget you.
  |<br>Be copy now to men of grosser blood,
  |<br>And teach them how to war. And you, good yeoman,
  |<br>Whose limbs were made in England, show us here
  |<br>The mettle of your pasture; let us swear
  |<br>That you are worth your breeding; which I doubt not;
  |<br>For there is none of you so mean and base,
  |<br>That hath not noble lustre in your eyes.
  |<br>I see you stand like greyhounds in the slips,
  |<br>Straining upon the start. The game's afoot:
  |<br>Follow your spirit, and upon this charge
  |<br>Cry 'God for Harry, England, and Saint George!'""".stripMargin)

  val p2: HtmlP = HtmlP.id("para2", "I'm going to link this footnote marker (2) to the footnote at the bottom of the intro text.",
  """<br>Once more unto the breach, dear friends, once more;
  |<br>Or close the wall up with our English dead.
  |<br>In peace there's nothing so becomes a man
  |<br>As modest stillness and humility:
  |<br>But when the blast of war blows in our ears,
  |<br>Then imitate the action of the tiger;
  |<br>Stiffen the sinews, summon up the blood,
  |<br>Disguise fair nature with hard-favour'd rage;
  |<br>Then lend the eye a terrible aspect;
  |<br>Let pry through the portage of the head
  |<br>Like the brass cannon; let the brow o'erwhelm it
  |<br>As fearfully as doth a galled rock
  |<br>O'erhang and jutty his confounded base,
  |<br>Swill'd with the wild and wasteful ocean.
  |<br>Now set the teeth and stretch the nostril wide,
  |<br>Hold hard the breath and bend up every spirit
  |<br>To his full height. On, on, you noblest English.
  |<br>Whose blood is fet from fathers of war-proof!
  |<br>Fathers that, like so many Alexanders,
  |<br>Have in these parts from morn till even fought
  |<br>And sheathed their swords for lack of argument:
  |<br>Dishonour not your mothers; now attest
  |<br>That those whom you call'd fathers did beget you.
  |<br>Be copy now to men of grosser blood,
  |<br>And teach them how to war. And you, good yeoman,
  |<br>Whose limbs were made in England, show us here
  |<br>The mettle of your pasture; let us swear
  |<br>That you are worth your breeding; which I doubt not;
  |<br>For there is none of you so mean and base,
  |<br>That hath not noble lustre in your eyes.
  |<br>I see you stand like greyhounds in the slips,
  |<br>Straining upon the start. The game's afoot:
  |<br>Follow your spirit, and upon this charge
  |<br>Cry 'God for Harry, England, and Saint George!'""".stripMargin)

  val p3: HtmlP = HtmlP.id("para3", "I'm going to link this footnote marker (3) to the footnote at the bottom of the intro text.")

  val n1 = HtmlP.id("note1", "A bit more context about paragraph 1.")
}
