package com.example.data.repository

import com.example.data.model.SafetyTopic

object SafetyData {
    val topics: List<SafetyTopic> = listOf(
        SafetyTopic(
            id = "safety_ppe",
            title = "Personal Protective Equipment (PPE)",
            iconEmoji = "🥽",
            category = "Basic Protection",
            guidelines = listOf(
                "Splash-proof safety goggles must be worn AT ALL TIMES when working with chemicals or glassware.",
                "Chemical-resistant nitrile gloves protect skin from corrosive acids, toxic heavy metals, and dyes.",
                "100% cotton lab coat prevents corrosive droplets from reaching personal clothing and skin.",
                "Closed-toe sturdy leather shoes are mandatory; open sandals or mesh sneakers are strictly forbidden.",
                "Long hair must be securely tied back; remove dangling jewelry and scarves."
            ),
            doList = listOf(
                "Put on goggles before uncapping any reagent bottle.",
                "Inspect gloves for pinholes before handling concentrated acids.",
                "Button lab coat fully to protect your torso."
            ),
            dontList = listOf(
                "Never rub your eyes or touch your face while wearing lab gloves.",
                "Never wear contact lenses during volatile solvent or acid procedures.",
                "Never leave the laboratory room while wearing contaminated gloves."
            ),
            emergencyAction = "If chemicals splash into eyes, immediately hold eyelids open at the Eye Wash Station and flush continuously with clean water for a full 15 minutes while shouting for teacher assistance."
        ),

        SafetyTopic(
            id = "safety_chemicals",
            title = "Safe Chemical Handling & Dilution",
            iconEmoji = "⚗️",
            category = "Chemical Protocols",
            guidelines = listOf(
                "The Golden Acid Rule: ALWAYS ADD ACID TO WATER (AAA). Never pour water into concentrated acid, as immense exothermic heat causes instant boiling and explosive acid spray.",
                "Never taste any chemical, and never smell vapors directly. Always 'waft' vapors toward your nose by waving your cupped hand gently over the tube mouth.",
                "Always double check reagent bottle labels (chemical formula, concentration, and date) before dispensing.",
                "Never return unused reagents back into original stock containers to avoid cross-contamination.",
                "Use a fume hood for reactions involving toxic, volatile, or choking fumes (e.g., concentrated HNO₃, Br₂, Cl₂)."
            ),
            doList = listOf(
                "Pour acids slowly down the side of a beaker containing water while stirring continuously.",
                "Replace bottle stoppers immediately after dispensing.",
                "Wipe reagent bottle exteriors if drops run down."
            ),
            dontList = listOf(
                "Never pipet liquids by mouth; always use rubber suction bulbs or mechanical pipette fillers.",
                "Never pour organic solvents down the domestic sink drain.",
                "Never place volatile solvents near open Bunsen burner flames."
            ),
            emergencyAction = "For skin contact with corrosive acid or alkali, immediately flood affected skin under running tap water for at least 15 minutes. Remove contaminated clothing."
        ),

        SafetyTopic(
            id = "safety_heating",
            title = "Bunsen Burner & Heating Safety",
            iconEmoji = "🔥",
            category = "Thermal Operations",
            guidelines = listOf(
                "Bunsen Burner Lighting: Ensure the air collar is CLOSED before lighting to produce a visible, luminous safety yellow flame.",
                "Once lit, open the air collar to obtain the hot, non-luminous roaring blue flame with an inner blue cone (the tip of the inner cone is the hottest zone ~1500°C).",
                "When heating a test tube, hold it with a wooden/metal test tube holder at a 45° angle, moving it gently through the flame.",
                "ALWAYS point the open mouth of a heated tube away from yourself and away from all other students.",
                "Never heat a closed, tightly stoppered system—thermal pressure expansion will cause violent shattering!"
            ),
            doList = listOf(
                "Ensure burner rubber tubing is snugly attached and free of cracks.",
                "Place wire gauze with ceramic center on the tripod stand to distribute heat evenly and prevent glassware cracking.",
                "Turn the air collar back to yellow safety flame when temporarily pausing heating."
            ),
            dontList = listOf(
                "Never leave an ignited Bunsen burner unattended.",
                "Never look directly down inside a heated boiling tube.",
                "Never touch hot glassware with bare fingers; hot glass looks identical to cold glass!"
            ),
            emergencyAction = "For thermal minor burns: Immediately place the burned area under cool, gently running water for 10-15 minutes. Do not apply ice, butter, or ointment."
        ),

        SafetyTopic(
            id = "safety_spills_fire",
            title = "Spill Response & Fire Emergency",
            iconEmoji = "🧯",
            category = "Emergency Procedures",
            guidelines = listOf(
                "Fire Extinguisher PASS Method: PULL the safety pin; AIM nozzle at the base of fire; SQUEEZE operating handle; SWEEP side-to-side.",
                "Acid Spill Neutralization: Sprinkle solid Sodium Bicarbonate (baking soda) around the perimeter and work inward until effervescence ceases, then wipe with damp towels.",
                "Base Spill Neutralization: Neutralize with dilute Acetic Acid (vinegar) or Boric Acid before clean up.",
                "Fire Blanket: If clothing catches fire, STOP, DROP, and ROLL, or wrap the victim tightly in a wool Fire Blanket to smother oxygen.",
                "Mercury or Heavy Metal Spills: Alert the teacher immediately; never vacuum mercury (it vaporizes into highly toxic gas)."
            ),
            doList = listOf(
                "Know the exact location of emergency fire blanket, fire extinguisher, eyewash station, and emergency shower.",
                "Report all spills, breakages, and accidents immediately to your lab instructor.",
                "Keep aisles and backpacks stored away from lab benches."
            ),
            dontList = listOf(
                "Never throw broken glass into general trash cans; use dedicated rigid 'Broken Glass Disposal' containers.",
                "Never attempt to clean up a concentrated solvent spill exceeding 500 mL alone.",
                "Never use water on a burning grease or flammable organic solvent fire."
            ),
            emergencyAction = "Sound the laboratory alarm, turn off main gas valves if safe to do so, and evacuate through the designated fire exit."
        )
    )

    data class HazardSymbol(val name: String, val icon: String, val meaning: String, val precautions: String)

    val hazardSymbols = listOf(
        HazardSymbol("Corrosive", "🧪⚠️", "Causes severe skin burns and serious eye damage. Attacks metals.", "Wear chemical splash goggles and nitrile gloves; work in tray."),
        HazardSymbol("Flammable", "🔥", "Catches fire easily upon exposure to spark, heat, or open flame.", "Keep away from Bunsen burners and electric hotplates."),
        HazardSymbol("Toxic / Poison", "☠️", "Fatal or toxic if swallowed, inhaled, or absorbed through skin.", "Dispense only inside fume hood; never pipet by mouth."),
        HazardSymbol("Oxidizing", "⭕🔥", "Releases oxygen readily; intensifies fires and causes violent deflagration.", "Store strictly away from organic solvents, papers, and combustibles."),
        HazardSymbol("Explosive", "💥", "Unstable substance capable of rapid mass detonation under friction or shock.", "Avoid percussion, friction, and localized overheating."),
        HazardSymbol("Health Hazard", "🫁⚠️", "Carcinogen, respiratory sensitizer, or target organ systemic toxicity.", "Minimize inhalation; use ventilation hoods and respirators."),
        HazardSymbol("Environmental", "🐟🌲", "Ecotoxic; causes long-term toxic hazards to aquatic ecosystems.", "Never dump in sink; collect in designated heavy metal waste drum.")
    )
}
