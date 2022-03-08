module cz.cuni.mff.mod.textprocessing.touppercase {
    requires cz.cuni.mff.mod.textprocessing.core;
    provides cz.cuni.mff.processor.textprocessor.definition.TextProcessor
            with cz.cuni.mff.processor.textprocessor.touppercase.ToUpperCaseTextProcessor;
}
