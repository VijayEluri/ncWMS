# Common utility routines

import re

class RequestParser:
    """ Parses request parameters from the URL.  Parameter values are
        case-sensitive, but their names are not.  Translates URL
        escape codes (e.g. %2F) to proper characters (e.g. /). """

    def __init__(self, queryString):
        """ queryString is the unprocessed query string from the URL """

        # Regular expressions for replacing URL escape codes
        # TODO there are many more
        self._urlCodes = {}
        self._urlCodes[re.compile("%2f", re.IGNORECASE)] = "/"
        self._urlCodes[re.compile("%20", re.IGNORECASE)] = " "
        self._urlCodes[re.compile("%3a", re.IGNORECASE)] = ":"
        self._urlCodes[re.compile("%2c", re.IGNORECASE)] = ","

        self._params = {} # Hashtable for query parameters and values
        if queryString is not None:
            for kvp in queryString.split("&"):
                keyAndVal = kvp.split("=")
                if len(keyAndVal) == 2:
                    (key, value) = keyAndVal
                    # We always store the key in lower case and escape
                    # the URL % codes
                    self._params[key.lower()] = self._escapeURLCodes(value).strip()

    def _escapeURLCodes(self, str):
        """ Replaces all the URL escape codes with their proper characters """
        for regexp in self._urlCodes.keys():
            str = regexp.sub(self._urlCodes[regexp], str)
        return str

    def getParamValue(self, key, default=None):
        """ Gets the value of the given parameter. If default==None
           and the parameter does not exist, a WMSException is thrown.
           Otherwise, the parameter value is returned, or the default
           value if it does not exist """
        if self._params.has_key(key.lower()):
            return self._params[key.lower()]
        elif default is None:
            raise WMSException("Must provide a " + key.upper() + " argument")
        else:
            return default