function execute(s1, s2) {
    if (!validate(s1, s2)) {
        return s2 + s1
    }
    return s1 + s2;
}