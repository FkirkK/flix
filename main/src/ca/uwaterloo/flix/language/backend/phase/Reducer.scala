package ca.uwaterloo.flix.language.backend.phase

import ca.uwaterloo.flix.language.backend.ir.{ReducedIR, SimplifiedAst}

object Reducer {

  // This phase: Introduce bit packing etc?
  // Inline things?
  // Apply partial evaluation.

  object Expressions {

    def reduce(tast: SimplifiedAst.Expression): ReducedIR.Expression = tast match {
      case _ => ???
    }

  }

}