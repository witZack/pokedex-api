package pokedex

import sbt._
import com.typesafe.sbt.SbtScalariform.ScalariformKeys

object Formatting {
  lazy val formatSettings = Seq(
    Compile / ScalariformKeys.preferences := formattingPreferences.value,
    Test / ScalariformKeys.preferences := formattingPreferences.value
  )

  def formattingPreferences = Def.setting {
    import scalariform.formatter.preferences._
    ScalariformKeys.preferences.value
      .setPreference(RewriteArrowSymbols, true)
      .setPreference(MultilineScaladocCommentsStartOnFirstLine, true)
      .setPreference(PlaceScaladocAsterisksBeneathSecondAsterisk, true)
      .setPreference(SpacesAroundMultiImports, true)
      .setPreference(IndentSpaces, 2)
      .setPreference(AlignParameters, true)
      .setPreference(AlignArguments, true)
      .setPreference(AlignSingleLineCaseStatements, true)
      .setPreference(DanglingCloseParenthesis, Preserve)
      .setPreference(DoubleIndentConstructorArguments, true)
  }

}