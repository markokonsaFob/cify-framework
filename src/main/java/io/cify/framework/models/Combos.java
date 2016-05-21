package io.cify.framework.models;

import io.cify.framework.Target;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@XmlRootElement(name = "combos")
@XmlAccessorType(XmlAccessType.FIELD)
public class Combos {

    @XmlElement(name = "combo")
    private List<Combo> comboList;

    public List<Combo> getComboList() {
        return comboList;
    }

    public Combo findComboForTarget(Target target) {

        Optional<Combo> combo = Optional.empty();

        switch (target) {
            case Desktop:
                combo = getComboList().stream().filter(Combo::isDesktop).findFirst();
                break;
            case Tablet:
                combo = getComboList().stream().filter(Combo::isTablet).findFirst();
                break;
            case Mobile:
                combo = getComboList().stream().filter(Combo::isMobile).findFirst();
                break;
            case TabletAndroid:
                combo = getComboList().stream().filter(Combo::isTabletAndroid).findFirst();
                break;
            case TabletIOS:
                combo = getComboList().stream().filter(Combo::isTabletIOS).findFirst();
                break;
            case MobileAndroid:
                combo = getComboList().stream().filter(Combo::isMobileAndroid).findFirst();
                break;
            case MobileIOS:
                combo = getComboList().stream().filter(Combo::isMobileIOS).findFirst();
                break;
            default:
                break;
        }

        if (combo.isPresent()) {
            return combo.get();
        }
        throw new NoSuchElementException("No combo found for target: " + target.name());
    }

}
