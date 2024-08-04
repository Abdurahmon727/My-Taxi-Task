package com.example.mytaxitask.core.extensions

import androidx.compose.ui.Modifier


/**
 * Used to apply modifiers conditionally.
 */
fun Modifier.ifElse(
    condition: () -> Boolean, ifTrueModifier: Modifier, ifFalseModifier: Modifier = Modifier
): Modifier = then(if (condition()) this.then(ifTrueModifier) else this.then(ifFalseModifier))

/**
 * Used to apply modifiers conditionally.
 */
fun Modifier.ifElse(
    condition: Boolean, ifTrueModifier: Modifier, ifFalseModifier: Modifier = Modifier
): Modifier = ifElse({ condition }, this.then(ifTrueModifier), this.then(ifFalseModifier))